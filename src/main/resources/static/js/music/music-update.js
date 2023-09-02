class MusicUpdateController extends UtilController {
    constructor() {
        super();
        this.musicCategoryListSelect = document.getElementById("musicCategoryListSelect");
        this.musicListSelect = document.getElementById("musicListSelect");
        this.updateMusicCategoryListSelect = document.getElementById("updateMusicCategoryListSelect");

        this.musicUpdateForm = document.getElementById("musicUpdateForm");
        this.updateIdInput = document.getElementById("updateIdInput");
        this.updateNameInput = document.getElementById("updateNameInput");
        this.updateArtistInput = document.getElementById("updateArtistInput");
        this.updateUrlInput = document.getElementById("updateUrlInput");
        this.updateCoverInput = document.getElementById("updateCoverInput");
        this.updateLrcInput = document.getElementById("updateLrcInput");
        this.updateCategoryIdInput = document.getElementById("updateCategoryIdInput");
        this.musicUpdateButton = document.getElementById("musicUpdateButton");
    }

    initMusicUpdateController() {
        this.initEventListener();
    }

    initEventListener() {
        this.updateMusicCategoryListSelect.addEventListener("change", evt => {
            this.updateCategoryIdInput.value = this.updateMusicCategoryListSelect.value;
        });

        this.musicListSelect.addEventListener("change", evt => {
            const xhr = new XMLHttpRequest();
            this.updateIdInput.value = this.musicListSelect.value;

            xhr.open("GET", `/music/search/${this.updateIdInput.value}`, true);

            xhr.addEventListener("loadend", event => {
                let status = event.target.status;

                if ((status >= 400 && status <= 500) || (status > 500)) {
                    this.showToastMessage(event.target.responseText);
                } else {
                    const responseValue = JSON.parse(event.target.responseText);
                    this.updateIdInput.value = responseValue["id"];
                    this.updateNameInput.value = responseValue["name"];
                    this.updateArtistInput.value = responseValue["artist"];
                    this.updateUrlInput.value = responseValue["url"];
                    this.updateCoverInput.value = responseValue["cover"];
                    this.updateLrcInput.value = responseValue["lrc"];
                    this.updateCategoryIdInput.value = responseValue["categoryId"];
                    this.updateMusicCategoryListSelect.value = responseValue["categoryId"];
                    this.updateMusicCategoryListSelect.text = responseValue["categoryName"];
                }
            });

            xhr.addEventListener("error", event => {
                this.showToastMessage('뮤직 정보 조회에 실패하였습니다.');
            });

            xhr.send();
        });

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
                }
            });

            xhr.addEventListener("error", event => {
                this.showToastMessage('뮤직 정보 조회에 실패하였습니다.');
            });

            xhr.send();
        });

        this.musicUpdateButton.addEventListener("click", evt => {
            if (confirm("뮤직 정보를 수정 하겠습니까?")) {
                this.musicUpdateForm.submit();
            }
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicUpdateController = new MusicUpdateController();
    musicUpdateController.initMusicUpdateController();
});