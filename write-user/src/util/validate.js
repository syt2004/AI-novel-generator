

/**
 * 字符串为空验证
 * @returns {(function(*, *): (Promise<never>))|*}
 */
export const isBlankValidate = () => {
    return (rule, value) => {
        if (value === '') {
            return Promise.reject("")
        } else {
            return Promise.resolve()
        }
    }
}


/**
 * 长度区间验证
 * @param min
 * @param max
 * @returns {(function(*, *): (Promise<never>))|*}
 */
export const rangeValidate = (min, max) => {
    return (rule, value) => {
        if (value.length < min || value.length > max) {
            return Promise.reject("")
        } else {
            return Promise.resolve()
        }
    }
}
