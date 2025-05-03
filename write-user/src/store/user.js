import {defineStore} from "pinia"
import {userinfo} from "../api/login.js";

export const useUserStore = defineStore(
    'user',
    {
        persist: true,
        state: () => ({
            avatar: '',
            username: '',
            nickname: '',
            words: [],
            registerGiftWords: ''
        }),
        getters: {

        },
        actions: {
            setUser(user) {
                this.username = user.username
                this.nickname = user.nickname
                this.avatar = user.avatar
                this.words = user.words
                this.registerGiftWords = user.registerGiftWords
            },
            updateUser() {
                userinfo().then(res => {
                    // fullscreenLoading.value = false
                    this.setUser(res.data)
                })
            }
        }
    }
)
