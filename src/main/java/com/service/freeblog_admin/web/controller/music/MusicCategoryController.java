package com.service.freeblog_admin.web.controller.music;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.domain.music.MusicCategory;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminException;
import com.service.freeblog_admin.web.model.music.MusicCategoryDeleteInput;
import com.service.freeblog_admin.web.model.music.MusicCategoryInput;
import com.service.freeblog_admin.web.model.music.MusicCategoryUpdateInput;
import com.service.freeblog_admin.web.service.music.MusicCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "음악 카테고리", description = "음악 카테고리관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/music-category")
public class MusicCategoryController {
    private final MusicCategoryService musicCategoryService;

    @Operation(summary = "음악 카테고리 추가 페이지 반환", description = "음악 카테고리 추가 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 카테고리 추가 정보가 담긴 관리 페이지")
    })
    @GetMapping("/add")
    public String musicCategoryAdd(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        model.addAttribute("musicCategoryInput", MusicCategoryInput.builder().build());
        return "music/music-category-add";
    }

    @Operation(summary = "음악 카테고리 수정 페이지 반환", description = "음악 카테고리 수정 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 카테고리 수정 정보가 담긴 관리 페이지")
    })
    @GetMapping("/update")
    public String musicCategoryUpdate(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        model.addAttribute("music_category_list", musicCategoryService.findMusicCategoryDtoList());
        return "music/music-category-update";
    }

    @Operation(summary = "음악 카테고리 수정 페이지 반환", description = "음악 카테고리 수정 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 카테고리 수정 정보가 담긴 관리 페이지")
    })
    @GetMapping("/delete")
    public String musicCategoryDelete(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        model.addAttribute("music_category_list", musicCategoryService.findMusicCategoryDtoList());
        return "music/music-category-delete";
    }

    @Operation(summary = "음악 카테고리 추가 작업", description = "음악 카테고리 추가 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 카테고리 추가 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 음악 카테고리 추가 작업 실패")
    })
    @PostMapping("/add")
    public String musicCategoryAdd(@Valid MusicCategoryInput musicCategoryInput, BindingResult bindingResult, Model model, Authentication authentication) {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        if (bindingResult.hasErrors()) {
            return "music/music-category-add";
        }

        MusicCategory musicCategory = MusicCategory.from(musicCategoryInput);
        musicCategoryService.musicCategoryAdd(musicCategory);

        return "redirect:/music-category/add";
    }

    @Operation(summary = "음악 카테고리 수정 작업", description = "음악 카테고리 수정 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 카테고리 수정 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 음악 카테고리 수정 작업 실패")
    })
    @PatchMapping("/update")
    public String musicCategoryUpdate(@Valid MusicCategoryUpdateInput musicCategoryUpdateInput, BindingResult bindingResult, Model model, Authentication authentication) {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        if (bindingResult.hasErrors()) {
            return "music/music-category-update";
        }

        musicCategoryService.updateMusicCategory(musicCategoryUpdateInput);
        return "redirect:/music-category/update";
    }


    @Operation(summary = "음악 카테고리 삭제 작업", description = "음악 카테고리 삭제 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 카테고리 삭제 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 음악 카테고리 삭제 작업 실패")
    })
    @DeleteMapping("/delete")
    public String musicCategoryDelete(MusicCategoryDeleteInput musicCategoryDeleteInput, BindingResult bindingResult, Model model, Authentication authentication) {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        if (bindingResult.hasErrors()) {
            return "music/music-category-delete";
        }

        musicCategoryService.deleteMusicCategory(musicCategoryDeleteInput);
        return "redirect:/music-category/delete";
    }
}
