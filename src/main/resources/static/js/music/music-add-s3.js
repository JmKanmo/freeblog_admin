class MusicAddS3Controller extends UtilController {
    constructor() {
        super();
        this.jsonUtilController = new JsonUtilController();
        this.musicAddS3Form = document.getElementById("musicS3AddForm");
        this.musicAddS3Button = document.getElementById("musicS3AddButton");
        this.uploadResultBox = document.getElementById("uploadResultBox");
    }

    initMusicAddS3Controller() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicAddS3Button.addEventListener("click", evt => {
            if (confirm("음악 정보를 S3에 등록 하겠습니까?")) {
                const xhr = new XMLHttpRequest();
                xhr.open("POST", `/music/add/s3`, true);

                xhr.addEventListener("loadend", event => {
                    let status = event.target.status;

                    if ((status >= 400 && status <= 500) || (status > 500)) {
                        this.showToastMessage(event.target.responseText);
                    } else {
                        this.showToastMessage("업로드에 성공하였습니다.");
                        this.uploadResultBox.appendChild(this.jsonUtilController.printJsonStrSyntaxHighlight(event.target.responseText));
                    }
                });

                xhr.addEventListener("error", event => {
                    this.showToastMessage("뮤직 정보를 S3에 등록하는데 실패하였습니다.");
                });

                xhr.send(new FormData(this.musicAddS3Form));
            }
        })
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicAddS3Controller = new MusicAddS3Controller();
    musicAddS3Controller.initMusicAddS3Controller();
});