import create from "./create.js"
import get from "./get.js"

export default(state = {}, action) => {
    return {
        ...state,
        create: create(state.create, action),
        get: get(state.get, action)
    }
}