class MusicCategoryUpdateController extends UtilController {
    constructor() {
        super();
        this.musicCategoryUpdateForm = document.getElementById("musicCategoryUpdateForm");
        this.musicCategoryList = document.getElementById("musicCategoryList");
        this.updateCategoryName = document.getElementById("updateCategoryName");
        this.musicCategoryHiddenSelectId = document.getElementById("music_category_hidden_select_id");
        this.musicCategoryHidenSelectName = document.getElementById("music_category_hidden_select_name");
        this.musicCategoryUpdateButton = document.getElementById("musicCategoryUpdateButton");
    }

    initMusicCategoryUpdateController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicCategoryUpdateButton.addEventListener("click", evt => {
            this.musicCategoryHiddenSelectId.value = this.musicCategoryList.value;
            this.musicCategoryHidenSelectName.value = this.updateCategoryName.value;
            this.#requestCategoryUpdate();
        })
    }

    #requestCategoryUpdate() {
        if (confirm("뮤직 카테고리를 수정 하겠습니까?")) {
            this.musicCategoryUpdateForm.submit();
        }
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicCategoryUpdateController = new MusicCategoryUpdateController();
    musicCategoryUpdateController.initMusicCategoryUpdateController();
});