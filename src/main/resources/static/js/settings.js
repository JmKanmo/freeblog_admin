class SettingsController extends HeaderController {
    constructor() {
        super();
        this.userBasicInfoEditButton = document.getElementById("user_basic_info_edit_button");
        this.passwordChangeButton = document.getElementById("password_change_button");
        this.withDrawButton = document.getElementById("with_draw_button");
    }

    initSettingsController() {
        this.initEventListener();
    }

    initEventListener() {
        this.userBasicInfoEditButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/user/update/basic-info', 'popup');
        });

        this.passwordChangeButton.addEventListener("click", evt => {
            this.openPopUp(1080, 500, '/user/update/password', 'popup');
        });

        this.withDrawButton.addEventListener("click", evt => {
            this.openPopUp(1080, 500, '/user/withdraw', 'popup');
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const settingsController = new SettingsController();
    settingsController.initSettingsController();
});