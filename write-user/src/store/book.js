import {defineStore} from "pinia"

export const useBookStore = defineStore(
    'book',
    {
        state: () => ({
                list: []
        }),
        getters: {

        },
        actions: {
                push(book) {
                        this.list.push(book)
                }
        }
    }
)
