package org.nr.backendtask.service;

import org.nr.backendtask.api.dto.FolderRequest;
import org.nr.backendtask.api.dto.FolderResponse;
import org.nr.backendtask.api.dto.PaginatedResponse;
import org.nr.backendtask.api.exceptions.ApiNotFoundException;
import org.nr.backendtask.api.exceptions.ApiUnhandledException;
import org.nr.backendtask.api.exceptions.ApiValidationException;
import org.nr.backendtask.model.ApplicationUser;
import org.springframework.data.domain.Pageable;

public interface FolderService {

    FolderResponse getOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException;

    boolean deleteOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException, ApiUnhandledException;

    PaginatedResponse<FolderResponse> findAllFolders(ApplicationUser applicationUser, Pageable pageable);

    FolderResponse createFolder(FolderRequest folderRequest, ApplicationUser applicationUser) throws ApiNotFoundException;

    FolderResponse updateFolder(Long id, FolderRequest updateFolderRequest, ApplicationUser applicationUser) throws ApiNotFoundException, ApiValidationException;
}
