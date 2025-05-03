import {defineStore} from "pinia"

export const useSettingStore = defineStore(
    'setting',
    {
        persist: true,
        state: () => ({
            theme: 'light'
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
