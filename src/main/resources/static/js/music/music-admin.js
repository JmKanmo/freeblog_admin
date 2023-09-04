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
    }

    initMusicController() {
        this.initEventListener();
        this.#initMusicPlayer();
    }

    #initMusicPlayer() {
        const selectMusicCategoryId = document.getElementById("musicCategoryList").value;
        document.getElementById("musicCategoryIdHiddenInput").value = selectMusicCategoryId;
        document.getElementById("musicCategoryNameHiddenInput").value = document.getElementById("musicCategoryList").options[selectMusicCategoryId].text;

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

    #requestMusicInfo() {
        const xhr = new XMLHttpRequest();
        const blogId = document.getElementById("blog_info_id").value;
        xhr.open("GET", `/post/recent/${blogId}`, true);

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            const responseValue = JSON.parse(event.target.responseText);

            if (((status >= 400 && status <= 500) || (status > 500)) || (status > 500)) {
                this.showToastMessage(responseValue["message"]);
            } else {
                // TODO
            }
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage("음악 정보를 불러오는데 실패하였습니다.");
        });
        xhr.send();
    }

    initEventListener() {
        this.musicCategoryList.addEventListener("change", evt => {
            const selectMusicCategoryId = document.getElementById("musicCategoryList").value;
            document.getElementById("musicCategoryIdHiddenInput").value = selectMusicCategoryId;
            document.getElementById("musicCategoryNameHiddenInput").value = document.getElementById("musicCategoryList").options[selectMusicCategoryId].text;

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
        });

        this.musicAddButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/add', 'popup');
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
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicController = new MusicController();
    musicController.initMusicController();
});