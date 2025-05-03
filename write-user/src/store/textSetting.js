import {defineStore} from "pinia"

export const useTextSettingStore = defineStore(
    'textSetting',
    {
        persist: true,
        state: () => ({
            fontSize: 16,
            fontLine: 1.5,
            // font: ''
        }),
        getters: {

        },
        actions: {
            lightTheme() {
                this.theme = 'light'
            },
            darkTheme() {
                this.theme = 'dark'
            },
        }
    }
)
