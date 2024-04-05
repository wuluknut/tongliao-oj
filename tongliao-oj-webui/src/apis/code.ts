export async function useApiContestList(page: number) {
  return await useApiFetch(`/code/contest?page=${page}`).get().json()
}

export async function useApiProblemList(page: number, keyword: string) {
  return await useApiFetch(`/code/problem?keyword=${keyword}&page=${page}`).get().json()
}

export async function useApiProblemInfo(id: string) {
  return await useApiFetch(`/code/problem/info?id=${id}`).get().json()
}

export async function useApiJudgeSubmit(id: number, code: string) {
  return await useApiFetch('/code/judge/submit').post(toFromData({ id, code })).json()
}

export async function useApiJudgeQuery(token: string) {
  return await useApiFetch(`/code/judge/query?token=${token}`).get().json()
}
