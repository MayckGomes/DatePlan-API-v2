package com.mayckgomes.dateplan_api.controllers;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.memories.CreateMemoryRequest;
import com.mayckgomes.dateplan_api.dto.memories.DeleteMemoryRequest;
import com.mayckgomes.dateplan_api.dto.memories.EditMemoryRequest;
import com.mayckgomes.dateplan_api.dto.memories.MemoryResponse;
import com.mayckgomes.dateplan_api.services.MemoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memories")
public class MemoryController {

    MemoryService memoryService;

    public MemoryController(MemoryService memoryService){
        this.memoryService = memoryService;
    }

    @GetMapping
    public ResponseEntity<List<MemoryResponse>> getMemories(
            Authentication authentication
    ){
        UserDomain user = (UserDomain) authentication.getPrincipal();

        return ResponseEntity.ok(memoryService.getMemories(user.getRelationshipId()));

    }

    @PostMapping("/create")
    public ResponseEntity<MemoryResponse> createMemory(
            @Valid @RequestBody CreateMemoryRequest memoryRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(memoryService.createMemory(memoryRequest));

    }

    @PutMapping("/edit")
    public ResponseEntity<MemoryResponse> editMemory(
            @Valid @RequestBody EditMemoryRequest memoryRequest){

        return ResponseEntity.ok(memoryService.editMemory(memoryRequest));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMemory(
            @Valid @RequestBody DeleteMemoryRequest memoryRequest,
            Authentication authentication){

        var user = (UserDomain) authentication.getPrincipal();

        memoryService.deleteMemory(memoryRequest.getMemoryId(), user.getRelationshipId());

        return ResponseEntity.noContent().build();

    }

}
