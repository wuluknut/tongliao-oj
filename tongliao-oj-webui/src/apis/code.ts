export async function useApiContestList(page: number) {
  return await useApiFetch(`/code/contest?page=${page}`).get().json()
}

export async function useApiContestInfo(id: string) {
  return await useApiFetch(`/code/contest/info?id=${id}`).get().json()
}

export async function useApiContestScore(id: string) {
  return await useApiFetch(`/code/contest/score?id=${id}`).get().json()
}

export async function useApiContestSubmit(id: number, scores: Array<number> | undefined) {
  return await useApiFetch('/code/contest/submit').post(toFromData({ id, scores })).json()
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
