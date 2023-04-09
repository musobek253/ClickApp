package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.*;
import com.musobek.clickapp.entity.*;
import com.musobek.clickapp.entity.enam.AccessType;
import com.musobek.clickapp.mapper.MapstructMapper;
import com.musobek.clickapp.repo.*;
import com.musobek.clickapp.service.impl.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceServiceimpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final WorkspaceRepository workspaceRepository;

    private final WorkspaceUserRepository workspaceUserRepository;
    private  final SpaceUserRepository spaceUserRepository;

    private final  SpaceClickAppsRepository spaceClickAppsRepository;
    private final ClickAppsRepository clickAppsRepository;

    private final UserRepository  userRepository;

    private final SpaceViewRepository spaceViewRepository;
    private final ViewRepository viewRepository;
    private  final MapstructMapper maper;
    @Override
    public ApiResponse get(Long id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty()){
            return new ApiResponse("Space not found",false);
        }
        Space space = optionalSpace.get();
        return new ApiResponse("OK",true,maper.toSpaceDto(space));
    }

    @Override
    public ApiResponse getWorspaceId(Long workspaceId) {
        List<Space> allByWorkspaceId = spaceRepository.findAllByWorkspaceId(workspaceId);
        if (allByWorkspaceId.isEmpty()){
            return new ApiResponse("Space not found", false);
        }
        return new ApiResponse("Ok",true,allByWorkspaceId);
    }

    @Override
    public ApiResponse addSpace(SpaceDto dto, User user) {
        if (spaceRepository.existsByNameAndWorkspaceId(dto.getName(), dto.getWorkspaceId())){
            return  new ApiResponse("All ready exist by space name",false);
        }
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (optionalWorkspace.isEmpty()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Space space = new Space();
        space.setIcon(null);
        space.setName(dto.getName());
        space.setColor(dto.getColor());
        space.setOwner(user);
        space.setWorkspace(workspace);
        space.setAccessType(dto.getAccessType());
        spaceRepository.save(space);
        List<SpaceUser> members = new ArrayList<>();
        if (dto.getAccessType().equals(AccessType.PUBLIC)){
            List<WorkspaceUser> allSpaceByWorkspaceId = workspaceUserRepository.findAllByWorkspaceIdId(dto.getWorkspaceId());
            allSpaceByWorkspaceId.stream().map(workspaceUser -> members.add(new SpaceUser(space,workspaceUser.getOwnerId()))).collect(Collectors.toList());
            spaceUserRepository.saveAll(members);
        }
        else {
            for (Long member : dto.getMembers()) {
                if (workspaceUserRepository.findByWorkspaceIdIdAndOwnerIdId(workspace.getId(),member).isPresent()){
                    members.add(new SpaceUser(space,userRepository.getReferenceById(member)));
                }
            }
            spaceUserRepository.save(new SpaceUser(space, user));
        }
        spaceClickAppsRepository.save(new SpaceClickApps(space, clickAppsRepository.getByName("Priority")));

        spaceViewRepository.save(new SpaceView(space,viewRepository.getByName("List")));

        return new ApiResponse("Creted", true);
    }

    @Override
    public ApiResponse editSpace(SpaceDto dto, Long id) {

            if (spaceRepository.existsByNameAndWorkspaceIdAndIdNot(dto.getName(), dto.getWorkspaceId(), id)) {
                return new ApiResponse("Space has already existed", false);
            }

            Optional<Space> optionalSpace = spaceRepository.findById(id);
            if (optionalSpace.isEmpty()) {
                return new ApiResponse("Space not found", false);
            }
            Space space = optionalSpace.get();
            space.setAvatar(null);
            space.setIcon(null);
            space.setColor(dto.getColor());
            space.setName(dto.getName());

            if (!space.getAccessType().equals(dto.getAccessType())) {
                space.setAccessType(dto.getAccessType());
                editMembers(space, dto.getMembers(), "SAVE");
            }

            return new ApiResponse("Updated", true);
        }

    @Override
    public ApiResponse attachMembers(AttachMemberDto dto, Long id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editMembers(space, dto.getMembers(), "SAVE");

        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse attachViews(AttachViewsDto dto, Long id) {
        Optional<Space> spaceOptional = spaceRepository.findById(id);
        if (spaceOptional.isEmpty()){
            return  new ApiResponse("Space not found", false);
        }
        Space space = spaceOptional.get();
        editViews(space,dto.getViews(),"Save");
        return new ApiResponse("Ok", true);
    }

    @Override
    public ApiResponse attachClickApp(AttachClickAppDto dto, Long id) {
        Optional<Space> spaceOptional = spaceRepository.findById(id);
        if (spaceOptional.isEmpty()){
            return  new ApiResponse("Space not found", false);
        }
        Space space = spaceOptional.get();
        editClickaps(space,dto.getClickapps(),"Save");
        return new ApiResponse("Ok", true);

    }

    @Override
    public ApiResponse detachMembers(AttachMemberDto dto, Long id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editMembers(space, dto.getMembers(), "DELETE");

        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse detachViews(AttachViewsDto dto, Long id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editViews(space, dto.getViews(), "DELETE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse detachClickApp(AttachClickAppDto dto, Long id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editClickaps(space,dto.getClickapps(),"DELETE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse delete(Long id) {
        if (!spaceRepository.existsById(id)){
            return new ApiResponse("Space not found", false);
        }
        spaceRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }

    @Override
    public ApiResponse getViewBySpace(Long id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        List<SpaceView> spaceViews = spaceViewRepository.findAllBySpaceIdId(space.getId());
        List<View> views = new ArrayList<>();
        for (SpaceView spaceView : spaceViews) {
            views.add(spaceView.getViewId());
        }
        return new ApiResponse("ok",true,views);
//                new ApiResponse("OK", true, mapper.toViewDto(views));
    }

    private void editMembers(Space space, List<Long> members, String operation) {
        List<SpaceUser> spaceUsers = spaceUserRepository.findAllBySpaceIdId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceUser> newMembers = new ArrayList<>(spaceUsers);
            for (Long memberId : members) {
                if (workspaceUserRepository.findByWorkspaceIdIdAndOwnerIdId(space.getWorkspace().getId(), memberId).isPresent()) {
                    boolean has = false;
                    for (SpaceUser spaceUser : spaceUsers) {
                        if (spaceUser.getUserId().getId().equals(memberId)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newMembers.add(new SpaceUser(space, userRepository.getById(memberId)));
                    }
                }
            }
            spaceUserRepository.saveAll(newMembers);
        } else if (operation.equals("DELETE")) {
            List<SpaceUser> deleting = new ArrayList<>();
            for (Long memberId : members) {
                for (SpaceUser spaceUser : spaceUsers) {
                    if (workspaceUserRepository.findByWorkspaceIdIdAndOwnerIdId(space.getWorkspace().getId(), memberId).isPresent()) {
                        if (memberId.equals(spaceUser.getUserId().getId())) {
                            deleting.add(spaceUser);
                        }
                    }
                }
            }
            spaceUserRepository.deleteAll(deleting);
        }
    }
    private void editViews(Space space, List<Integer> views, String operation) {
        List<SpaceView> spaceViews = spaceViewRepository.findAllBySpaceIdId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceView> newViews = new ArrayList<>(spaceViews);
            for (Integer viewId : views) {
                if (viewRepository.findById(viewId).isPresent()) {
                    boolean has = false;
                    for (SpaceView spaceView : spaceViews) {
                        if (spaceView.getViewId().getId().equals(viewId)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newViews.add(new SpaceView( space, viewRepository.getById(viewId)));
                    }
                }
            }
            spaceViewRepository.saveAll(newViews);
        } else if (operation.equals("DELETE")) {
            List<SpaceView> deleting = new ArrayList<>();
            for (Integer viewId : views) {
                for (SpaceView spaceView : spaceViews) {
                    if (viewRepository.findById(viewId).isPresent() && viewId.equals(spaceView.getViewId().getId())) {
                        deleting.add(spaceView);
                    }
                }
            }
            spaceViewRepository.deleteAll(deleting);
        }
    }
    private void editClickaps(Space space , List<Integer>clickApps, String option){
        List<SpaceClickApps> allBySpaceIdId = spaceClickAppsRepository.findAllBySpaceIdId(space.getId());
        if (option.equals("Save")){
            List<SpaceClickApps> newClick = new ArrayList<>(allBySpaceIdId);
            for (Integer clickAps: clickApps){
                boolean has = false;
                for (SpaceClickApps spaceClickApps1 : allBySpaceIdId) {
                    if (spaceClickApps1.getClickAppsId().getId().equals(clickApps)) {
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    newClick.add(new SpaceClickApps( space,clickAppsRepository.getById(clickAps)));
                }
            }
            spaceClickAppsRepository.saveAll(newClick);
        }
        else if (option.equals("DELETE")) {
            List<SpaceClickApps> deleting = new ArrayList<>();
            for (Integer clickApp : clickApps) {
                for (SpaceClickApps clickApps1 : allBySpaceIdId) {
                    if (clickAppsRepository.findById(clickApp).isPresent() && clickApp.equals(clickApps1.getClickAppsId().getId())) {
                        deleting.add(clickApps1);
                    }
                }
            }
            spaceClickAppsRepository.deleteAll(deleting);
        }

    }

}
