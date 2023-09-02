class MusicAddController extends UtilController {
    constructor() {
        super();
        this.musicCategoryList = document.getElementById("musicCategoryList");
        this.musicCategoryIdHiddenInput = document.getElementById("musicCategoryIdHiddenInput");
        this.musicCategoryNameHiddenInput = document.getElementById("musicCategoryNameHiddenInput");
        this.musicAddForm = document.getElementById("musicAddForm");
        this.musicName = document.getElementById("music_name");
        this.musicArtist = document.getElementById("music_artist");
        this.musicUrl = document.getElementById("music_url");
        this.musicCover = document.getElementById("music_cover");
        this.musicLrc = document.getElementById("music_lrc");
        this.musicAddButton = document.getElementById("musicAddButton");
        this.musicTestButton = document.getElementById("musicTestButton");
        this.musicTestContainer = document.getElementById("musicTestContainer");
        this.musicUtilController = new MusicUtilController();
    }

    initMusicAddController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicCategoryList.addEventListener("click", evt => {
            this.musicCategoryIdHiddenInput.value = this.musicCategoryList.value;
        });

        this.musicAddButton.addEventListener("click", evt => {
            if (confirm("뮤직 정보를 추가 하겠습니까?")) {
                this.musicAddForm.submit();
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

            musicCategoryMap.set(this.musicCategoryNameHiddenInput.value, {
                name: this.musicCategoryNameHiddenInput.value,
                audio: [
                    {
                        name: this.musicName.value,
                        artist: this.musicArtist.value,
                        url: this.musicUrl.value,
                        cover: this.musicCover.value,
                        theme: this.musicCategoryNameHiddenInput.value
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
    const musicAddController = new MusicAddController();
    musicAddController.initMusicAddController();
});