class MusicUtilController {
    constructor() {
        this.musicPlayer = new Map();
        this.documentId = `audio_player`;
    }

    initAudioPlayer(musicMap, find) {
        const musicConfig = musicMap.get("config");
        const musicData = musicMap.get("data");

        if (musicMap && musicConfig) {
            musicData.forEach((value, key) => {
                const category = key;
                const audios = value["audio"];

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
            });
        }
        return null;
    }
}