export async function useApiCommentList(page: number, parentId: string) {
  return await useApiFetch(`/forum/comment?parentId=${parentId}&page=${page}`).get().json()
}

export async function useApiComment(parentId: string, content: string) {
  return await useApiFetch('/forum/comment').post(toFromData({ parentId, content })).json()
}

export async function useApiDiscussionList(page: number, keyword: string, me: boolean) {
  return await useApiFetch(`/forum/discussion?me=${me}&keyword=${keyword}&page=${page}`).get().json()
}

export async function useApiDiscussion(title: string, content: string) {
  return await useApiFetch('/forum/discussion').post(toFromData({ title, content })).json()
}

export async function useApiDiscussionInfo(id: string) {
  return await useApiFetch(`/forum/discussion/info?id=${id}`).get().json()
}

export async function useApiForumDelete(id: number, is: boolean) {
  return await useApiFetch('/forum/delete').post(toFromData({ id, is })).json()
}

export async function useApiForumStar(id: number, is: boolean) {
  return await useApiFetch('/forum/star').post(toFromData({ id, is })).json()
}
