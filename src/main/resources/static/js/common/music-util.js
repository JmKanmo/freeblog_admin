class MusicUtilController {
    constructor() {
        this.musicPlayer = new Map();
        this.documentId = `audio_player`;
    }

    initAudioPlayer(musicMap, find) {
        const musicConfig = musicMap["config"];
        const musicData = musicMap["data"];
        if (musicMap) {
            for (const data in musicData) {
                const category = musicData[data]["name"];
                const audios = musicData[data]["audio"];

                this.musicPlayer.set(category, new APlayer({
                    container: document.getElementById(this.documentId),
                    listFolded: musicConfig["listFolded"],
                    listMaxHeight: musicConfig["listMaxHeight"],
                    lrcType: musicConfig["lrcType"],
                    autoplay: musicConfig["autoplay"],
                    mutex: musicConfig["mutex"],
                    order: musicConfig["order"],
                    mini: musicConfig["mini"],
                    fixed: musicConfig["fixed"],
                    audio: audios
                }));
            }
        }
        return null;
    }
}