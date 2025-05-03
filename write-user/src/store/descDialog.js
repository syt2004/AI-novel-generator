import {infoTemplate} from "../api/template.js";

export const useDescDialogStore = defineStore(
    'descDialog',
    {
        state: () => ({
            show: false,
            bookId: null,
            template: {}
        }),
        getters: {},
        actions: {
            showDialog() {
                this.show = true
            },
            hiddenDialog() {
                this.show = false
            },

        }
    }
)
