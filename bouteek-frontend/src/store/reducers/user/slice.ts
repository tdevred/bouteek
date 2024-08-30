import { createAppSlice } from "../buildCreateSlice";
import axios from "axios";

interface UserState {
    loading: boolean,
    isLoggedIn: boolean,
    username?: string,
    token?: string
}

type User = {
    username: string,
    token: string
}

type UserLoginData = {
    email: string,
    password: string
}

const initialState = {
    loading: true,
    isLoggedIn: false
} satisfies UserState as UserState

const userSlice = createAppSlice({
    name: 'user',
    initialState,
    reducers: (create) => ({
        connect: create.asyncThunk(
            async (loginData: UserLoginData) => {
                const res = await axios.post("http://localhost:8080/auth/login", loginData)
                return res.data as User
            },
            {
                pending: (state) => {
                    state.loading = true
                },
                rejected: (state, action) => {
                    console.log("error in action", action.error)
                    state.loading = false
                },
                fulfilled: (state, action) => {
                    state.loading = false
                    state.username = action.payload.username
                    state.token = action.payload.token
                    state.isLoggedIn = true
                }
            }
        ),
        disconnect: create.reducer<void>((state) => {
            delete state.username;
            delete state.token;
            state.isLoggedIn = false;
        })
    })
})

export const { connect, disconnect } = userSlice.actions
export default userSlice.reducer