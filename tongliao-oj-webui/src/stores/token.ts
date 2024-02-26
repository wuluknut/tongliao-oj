export const useTokenStore = defineStore('token', () => {
  const key = 'token'

  const anonymous = 'anonymous'

  const token = (token?: string): string => {
    if (token !== undefined) {
      localStorage.setItem(key, token)
      return token
    } else {
      return localStorage.getItem(key) || anonymous
    }
  }

  const logout = () => {
    localStorage.removeItem(key)

    useRouter().push('/')
  }

  const isAuthenticated = () => {
    return token() !== anonymous
  }

  const username = () => {
    return !isAuthenticated() ? anonymous : JSON.parse(atob(token().split('.')[1])).username
  }

  return { token, logout, isAuthenticated, username }
})
