class MusicAddS3Controller extends UtilController {
    constructor() {
        super();
        this.musicAddS3Form = document.getElementById("musicS3AddForm");
        this.musicAddS3Button = document.getElementById("musicS3AddButton");
    }

    initMusicAddS3Controller() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicAddS3Button.addEventListener("click", evt => {
            if (confirm("음악 정보를 S3에 등록 하겠습니까?")) {
                this.musicAddS3Form.submit();
            }
        })
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicAddS3Controller = new MusicAddS3Controller();
    musicAddS3Controller.initMusicAddS3Controller();
});