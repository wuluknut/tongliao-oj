export async function useApiContestList(page: number) {
  return await useApiFetch(`/code/contest?page=${page}`).get().json()
}

export async function useApiProblemList(page: number, keyword: string) {
  return await useApiFetch(`/code/problem?keyword=${keyword}&page=${page}`).get().json()
}
