package com.service.freeblog_admin.web.controller.music;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.dto.music.MusicCategoryDto;
import com.service.freeblog_admin.web.dto.music.MusicDto;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminException;
import com.service.freeblog_admin.web.model.music.MusicAddInput;
import com.service.freeblog_admin.web.model.music.MusicCategoryDeleteInput;
import com.service.freeblog_admin.web.model.music.MusicDeleteInput;
import com.service.freeblog_admin.web.model.music.MusicUpdateInput;
import com.service.freeblog_admin.web.service.music.MusicCategoryService;
import com.service.freeblog_admin.web.service.music.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Tag(name = "음악", description = "음악 관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/music")
@Slf4j
public class MusicController {
    private final MusicService musicService;
    private final MusicCategoryService musicCategoryService;

    @Operation(summary = "음악관리 페이지 반환", description = "음악관리 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악관리 정보가 담긴 관리 페이지")
    })
    @GetMapping("/admin")
    public String musicAdmin(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        model.addAttribute("music_category_list", musicCategoryService.findMusicCategoryDtoList());
        return "music/music-admin";
    }

    @Operation(summary = "음악 추가 페이지 반환", description = "음악 추가 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 추가 정보가 담긴 관리 페이지")
    })
    @GetMapping("/add")
    public String musicAdd(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        model.addAttribute("music_category_list", musicCategoryService.findMusicCategoryDtoList());
        model.addAttribute("musicAddInput", MusicAddInput.builder().build());
        return "music/music-add";
    }

    @Operation(summary = "음악 S3 추가 페이지 반환", description = "음악 S3 추가 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 S3 추가 정보가 담긴 관리 페이지")
    })
    @GetMapping("/add/s3")
    public String musicS3Add(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        return "music/music-add-s3";
    }

    @Operation(summary = "음악 설정 페이지 반환", description = "음악 설정 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 설정 정보가 담긴 관리 페이지")
    })
    @GetMapping("/update")
    public String musicUpdate(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        List<MusicCategoryDto> musicCategoryDtoList = musicCategoryService.findMusicCategoryDtoList();
        List<MusicDto> musicDtoList = musicCategoryDtoList.isEmpty() ? Collections.emptyList() : musicCategoryService.findMusicDtoByCategoryId(musicCategoryDtoList.get(0).getId());
        MusicDto musicDto = musicDtoList.isEmpty() ? MusicDto.builder().build() : musicService.findMusicDtoById(musicDtoList.get(0).getId());

        model.addAttribute("music_category_list", musicCategoryDtoList);
        model.addAttribute("music_list", musicDtoList);
        model.addAttribute("musicUpdateInput", MusicUpdateInput.from(musicDto));
        return "music/music-update";
    }

    @Operation(summary = "음악 설정 페이지 반환", description = "음악 설정 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 설정 정보가 담긴 관리 페이지")
    })
    @GetMapping("/delete")
    public String musicDelete(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        List<MusicCategoryDto> musicCategoryDtoList = musicCategoryService.findMusicCategoryDtoList();
        List<MusicDto> musicDtoList = musicCategoryDtoList.isEmpty() ? Collections.emptyList() : musicCategoryService.findMusicDtoByCategoryId(musicCategoryDtoList.get(0).getId());
        MusicDto musicDto = musicDtoList.isEmpty() ? MusicDto.builder().build() : musicService.findMusicDtoById(musicDtoList.get(0).getId());

        model.addAttribute("music_category_list", musicCategoryDtoList);
        model.addAttribute("music_list", musicDtoList);
        model.addAttribute("musicDeleteInput", MusicDeleteInput.from(musicDto));
        return "music/music-delete";
    }

    @Operation(summary = "음악 삭제 작업", description = "음악 삭제 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 삭제 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 음악 삭제 작업 실패")
    })
    @DeleteMapping("/delete")
    public String musicDelete(MusicDeleteInput musicDeleteInput, BindingResult bindingResult, Model model, Authentication authentication) {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        if (bindingResult.hasErrors()) {
            return "music/music-delete";
        }

        musicService.musicDelete(musicDeleteInput);
        return "redirect:/music/delete";
    }

    @Operation(summary = "음악 추가 작업", description = "음악 추가 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 추가 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 음악 추가 작업 실패")
    })
    @PostMapping("/add")
    public String musicAdd(@Valid MusicAddInput musicAddInput, BindingResult bindingResult, Model model, Authentication authentication) {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        if (bindingResult.hasErrors()) {
            return "music/music-add";
        }

        musicService.musicAdd(musicAddInput);
        return "redirect:/music/add";
    }

    @Operation(summary = "음악 추가 작업", description = "음악 추가 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 추가 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 음악 추가 작업 실패")
    })
    @PostMapping("/add/s3")
    public String musicAddS3(
            @RequestParam("musicFile") MultipartFile musicFile,
            @RequestParam("musicImage") MultipartFile musicImage, Model model,
            Authentication authentication) {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        // TODO
        return "redirect:/music/add/s3";
    }

    @Operation(summary = "음악 수정 작업", description = "음악 수정 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 수정 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 음악 수정 작업 실패")
    })
    @PatchMapping("/update")
    public String musicUpdate(@Valid MusicUpdateInput musicUpdateInput, BindingResult bindingResult, Model model, Authentication authentication) {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        if (bindingResult.hasErrors()) {
            return "music/music-update";
        }

        musicService.musicUpdate(musicUpdateInput);
        return "redirect:/music/update";
    }
}
