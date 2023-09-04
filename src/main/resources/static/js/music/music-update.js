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
        this.updateCategoryNameInput = document.getElementById("updateCategoryNameInput");
        this.musicUpdateButton = document.getElementById("musicUpdateButton");

        this.musicTestContainer = document.getElementById("musicTestContainer");
        this.musicTestButton = document.getElementById("musicTestButton");
        this.musicUtilController = new MusicUtilController();
    }

    initMusicUpdateController() {
        this.initEventListener();
    }

    initEventListener() {
        this.updateMusicCategoryListSelect.addEventListener("change", evt => {
            this.updateCategoryIdInput.value = this.updateMusicCategoryListSelect.value;
            let updateCategoryOption = this.updateMusicCategoryListSelect.options[this.updateCategoryIdInput.value];
            this.updateCategoryNameInput.value = updateCategoryOption.text;
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
                    this.updateCategoryNameInput.value = this.updateMusicCategoryListSelect.text;
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

        this.musicTestButton.addEventListener("click", evt => {
            const musicTestPlayerElement = document.createElement('div');
            musicTestPlayerElement.className = `audio_player_style`;
            musicTestPlayerElement.id = `audio_player`;
            this.musicTestContainer.appendChild(musicTestPlayerElement);

            const musicMap = new Map();
            const musicCategoryMap = new Map();
            const musicConfigMap = new Map();

            // 기본 값 지정
            musicConfigMap.set('config', {
                listFolded: true,
                listMaxHeight: 90,
                lrcType: 0,
                autoplay: false,
                mutex: true,
                order: 'random',
                mode: {
                    fixed: true,
                    mini: false
                }
            });

            musicCategoryMap.set(this.updateCategoryNameInput.value, {
                name: this.updateCategoryNameInput.value,
                audio: [
                    {
                        name: this.updateNameInput.value,
                        artist: this.updateArtistInput.value,
                        url: this.updateUrlInput.value,
                        cover: this.updateCoverInput.value,
                        theme: this.updateCategoryNameInput.value
                    }
                ]
            });

            musicMap.set('data', musicCategoryMap);
            musicMap.set('config', musicConfigMap.get('config'));

            this.musicUtilController.initAudioPlayer(musicMap);
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicUpdateController = new MusicUpdateController();
    musicUpdateController.initMusicUpdateController();
});