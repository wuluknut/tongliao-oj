export async function useApiUser() {
  return await useApiFetch('/user').get().json()
}

export async function useApiPassword(password: string, newPassword: string) {
  return await useApiFetch('/user').post(toFromData({ password, newPassword })).json()
}

export async function useApiLogin(username: string, password: string) {
  return await useApiFetch(
    '/login',
    {
      afterFetch({ data, response }) {
        data.status === 200 && useTokenStore().token(data.content)

        return { data, response }
      },
    },
  ).post(toFromData({ username, password })).json()
}

export async function useApiLogout() {
  return await useApiFetch(
    '/logout',
    {
      afterFetch({ data, response }) {
        data.status === 200 && useTokenStore().logout()

        return { data, response }
      },
    },
  ).get().json()
}
