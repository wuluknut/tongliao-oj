import { createFetch } from '@vueuse/core'

export const useApiFetch = createFetch({
  baseUrl: import.meta.env.MODE === 'development' ? '/api' : '',
  options: {
    timeout: 10000,
    async beforeFetch({ options }) {
      options.headers = {
        ...options.headers,
        Authorization: `Bearer ${useTokenStore().token()}`,
      }

      return { options }
    },
    afterFetch({ data, response }) {
      data.status === 401 && ElMessage.error(data.message)
      data.status === 403 && ElMessage.error(data.message) && useTokenStore().logout()

      response.headers.get('refresh') && useTokenStore().token(response.headers.get('refresh')!)

      return { data, response }
    },
    onFetchError({ data, error }) {
      ElMessage.error('请求错误！')

      return { data, error }
    },
  },
  fetchOptions: {
    mode: 'cors',
  },
})

export function toFromData(obj: Record<string, any>) {
  const fromData = new FormData()

  obj && Object.keys(obj).forEach((key) => {
    fromData.append(key, obj[key])
  })

  return fromData
}
