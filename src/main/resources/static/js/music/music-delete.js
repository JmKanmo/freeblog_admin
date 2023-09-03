class MusicDeleteController extends UtilController {
    constructor() {
        super();
        this.musicCategoryListSelect = document.getElementById("musicCategoryListSelect");
        this.musicListSelect = document.getElementById("musicListSelect");
        this.deleteIdHiddenInput = document.getElementById("deleteIdHiddenInput");
        this.deleteCategoryIdHiddenInput = document.getElementById("deleteCategoryIdHiddenInput");
        this.musicDeleteForm = document.getElementById("musicDeleteForm");
        this.musicDeleteButton = document.getElementById("musicDeleteButton");
    }

    initMusicDeleteController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicCategoryListSelect.addEventListener("change", evt => {
            const xhr = new XMLHttpRequest();
            const musicCategoryId = this.musicCategoryListSelect.value;

            xhr.open("GET", `/music-category/search/music/${musicCategoryId}`, true);

            xhr.addEventListener("loadend", event => {
                let status = event.target.status;

                if ((status >= 400 && status <= 500) || (status > 500)) {
                    this.showToastMessage(event.target.responseText);
                } else {
                    const responseValue = JSON.parse(event.target.responseText);
                    this.musicListSelect.options.length = 0;

                    for (let i = 0; i < responseValue.length; i++) {
                        const optionTag = document.createElement('option');
                        optionTag.value = responseValue[i]["id"];
                        optionTag.text = responseValue[i]["name"] + '-' + responseValue[i]["artist"];
                        this.musicListSelect.add(optionTag);
                    }
                    this.deleteCategoryIdHiddenInput.value = musicCategoryId;
                }
            });

            xhr.addEventListener("error", event => {
                this.showToastMessage('뮤직 정보 조회에 실패하였습니다.');
            });

            xhr.send();
        });

        this.musicListSelect.addEventListener("change", evt => {
            this.deleteIdHiddenInput.value = this.musicListSelect.value;
        });

        this.musicListSelect.addEventListener("click", evt => {

        });

        this.musicDeleteButton.addEventListener("click", evt => {
            if (confirm("뮤직 정보를 삭제 하겠습니까?")) {
                this.musicDeleteForm.submit();
            }
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicDeleteController = new MusicDeleteController();
    musicDeleteController.initMusicDeleteController();
});