package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.TagEditDto;
import com.musobek.clickapp.entity.Tag;
import com.musobek.clickapp.mapper.MapstructMapper;
import com.musobek.clickapp.repo.TagRepository;
import com.musobek.clickapp.repo.WorkspaceRepository;
import com.musobek.clickapp.service.impl.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceimpl implements TagService {
    private final TagRepository tagRepo;

    private final WorkspaceRepository workspaceRepository;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse get(Long workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            return new ApiResponse("Workspace not found", false);
        }
        return new ApiResponse("OK", true, mapper.toTagDto(tagRepo.findAllByWorkspaceIdId(workspaceId)));
    }

    @Override
    public ApiResponse editTag(Integer tagId, TagEditDto dto) {
        Optional<Tag> optionalTag = tagRepo.findById(tagId);
        if (!optionalTag.isPresent()) {
            return new ApiResponse("Tag not found", false);
        }
        Tag tag = optionalTag.get();
        tag.setName(dto.getName());
        tag.setColor(dto.getColor());
        tagRepo.save(tag);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse deleteTag(Integer tagId) {
        if (!tagRepo.existsById(tagId)) {
            return new ApiResponse("Tag not found", false);
        }
        tagRepo.deleteById(tagId);
        return new ApiResponse("Deleted", true);
    }
}
