export default defineNuxtRouteMiddleware((to) => {
  if (to.name !== undefined) {
    if (to.name === 'index' && useTokenStore().isAuthenticated()) {
      return navigateTo('/system')
    }
    if (to.name !== 'index' && !useTokenStore().isAuthenticated()) {
      return navigateTo('/')
    }
  }
})
