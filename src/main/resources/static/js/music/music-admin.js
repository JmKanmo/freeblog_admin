class MusicController extends UtilController {
    constructor() {
        super();
        this.musicUtilController = new MusicUtilController();
        this.musicCategoryList = document.getElementById("musicCategoryList");
        this.musicAddButton = document.getElementById("musicAddButton");
        this.musicUpdateButton = document.getElementById("musicUpdateButton");
        this.musicDeleteButton = document.getElementById("musicDeleteButton");
        this.musicCategoryAddButton = document.getElementById("musicCategoryAddButton");
        this.musicCategoryUpdateButton = document.getElementById("musicCategoryUpdateButton");
        this.musicCategoryDeleteButton = document.getElementById("musicCategoryDeleteButton");
        this.musicS3AddButton = document.getElementById("musicS3AddButton");
        this.musicS3SearchButton = document.getElementById("musicS3SearchButton");
    }

    initMusicController() {
        this.initEventListener();
        this.initMusicPlayer();
    }

    initMusicPlayer() {
        const selectMusicCategoryId = !this.musicCategoryList.value ? this.musicUtilController.TOTAL_MUSIC_CATEGORY_INDEX : this.musicCategoryList.value;
        const selectMusicCategoryIndex = this.musicCategoryList.selectedIndex;
        const selectMusicCategoryListOptions = this.musicCategoryList.options[selectMusicCategoryIndex];

        document.getElementById("musicCategoryIdHiddenInput").value = selectMusicCategoryId;

        if (!selectMusicCategoryListOptions) {
            const option = document.createElement("option");
            option.text = "없음";
            option.value = "0";
            this.musicCategoryList.add(option);
            this.musicUtilController.setAudioPlayer(null);
            return;
        }

        document.getElementById("musicCategoryNameHiddenInput").value = this.musicCategoryList.options[selectMusicCategoryIndex].text;
        this.#requestMusicInfo(selectMusicCategoryId);
    }

    initEventListener() {
        this.musicCategoryList.addEventListener("change", evt => {
            const selectMusicCategoryId = this.musicCategoryList.value;
            const selectMusicCategoryIndex = this.musicCategoryList.selectedIndex;
            document.getElementById("musicCategoryIdHiddenInput").value = selectMusicCategoryId;
            document.getElementById("musicCategoryNameHiddenInput").value = this.musicCategoryList.options[selectMusicCategoryIndex].text;
            this.#requestMusicCategoryInfo(selectMusicCategoryId);
        });

        this.musicAddButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/add', 'popup');
        });

        this.musicS3SearchButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/search/s3', 'popup');
        });

        this.musicS3AddButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/add/s3', 'popup');
        });

        this.musicUpdateButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/update', 'popup');
        });

        this.musicDeleteButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/delete', 'popup');
        });

        this.musicCategoryAddButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music-category/add', 'popup');
        });

        this.musicCategoryUpdateButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music-category/update', 'popup');
        });

        this.musicCategoryDeleteButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music-category/delete', 'popup');
        });
    }

    #requestMusicInfo(selectMusicCategoryId) {
        const xhr = new XMLHttpRequest();

        xhr.open("GET", `/music-category/search/music/${selectMusicCategoryId}`, true);

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;

            if (((status >= 400 && status <= 500) || (status > 500)) || (status > 500)) {
                this.showToastMessage(event.target.responseText);
            } else {
                const responseValue = JSON.parse(event.target.responseText);
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

                for (let idx in responseValue) {
                    const musicData = responseValue[idx];
                    const categoryId = musicData["categoryId"];
                    const mapValue = musicCategoryMap.get(categoryId);

                    if (mapValue) {
                        const audioList = mapValue["audio"];
                        audioList.push({
                            name: musicData["name"],
                            artist: musicData["artist"],
                            url: musicData["url"],
                            cover: musicData["cover"],
                            theme: musicData["categoryName"],
                        });
                    } else {
                        musicCategoryMap.set(categoryId, {
                            name: musicData["categoryName"],
                            audio: [
                                {
                                    name: musicData["name"],
                                    artist: musicData["artist"],
                                    url: musicData["url"],
                                    cover: musicData["cover"],
                                    theme: musicData["categoryName"],
                                }
                            ]
                        });
                    }
                    idx++;
                }
                musicMap.set('data', musicCategoryMap);
                musicMap.set('config', musicConfigMap.get('config'));

                this.musicUtilController.initAudioPlayer(musicMap);
            }
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage("뮤직 정보를 불러오는데 실패하였습니다.");
        });
        xhr.send();
    }

    #requestMusicCategoryInfo(selectMusicCategoryId) {
        const xhr = new XMLHttpRequest();

        xhr.open("GET", `/music-category/search/music/${selectMusicCategoryId}`, true);

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;

            if (((status >= 400 && status <= 500) || (status > 500)) || (status > 500)) {
                this.showToastMessage(event.target.responseText);
            } else {
                this.musicUtilController.setAudioPlayer(event.target.responseText);
            }
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage("뮤직 정보를 불러오는데 실패하였습니다.");
        });
        xhr.send();
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicController = new MusicController();
    musicController.initMusicController();
});