class MusicCategoryUpdateController extends UtilController {
    constructor() {
        super();
        this.musicCategoryList = document.getElementById("musicCategoryList");
        this.updateCategory = document.getElementById("updateCategory");
        this.musicCategorySelectId = document.getElementById("music_category_select_id");
        this.musicCategoryHiddenSelectName = document.getElementById("music_category_hidden_select_id");
        this.musicCategoryHidenUpdateButton = document.getElementById("music_category_hidden_select_name");
        this.musicCategoryUpdateButton = document.getElementById("musicCategoryUpdateButton");
    }

    initMusicCategoryUpdateController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicCategoryUpdateButton.addEventListener("click", evt => {
            this.showToastMessage("음악 카테고리 정보 수정");
        })
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicCategoryUpdateController = new MusicCategoryUpdateController();
    musicCategoryUpdateController.initMusicCategoryUpdateController();
});