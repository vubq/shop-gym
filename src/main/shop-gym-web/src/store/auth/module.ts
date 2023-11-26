import AuthService from '@/services/auth/service';

const user = JSON.parse(localStorage.getItem('user') as any);
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }: any, user: any) {
      return AuthService.login(user).then(
        (user: any) => {
          commit('loginSuccess', user);
          return Promise.resolve(user);
        },
        (error: any) => {
          commit('loginFailure');
          return Promise.reject(error);
        }
      );
    },
    logout({ commit }: any) {
      AuthService.logout();
      commit('logout');
    },
    register({ commit }: any, user: any) {
      return AuthService.register(user).then(
        (response: any) => {
          commit('registerSuccess');
          return Promise.resolve(response.data);
        },
        (error: any) => {
          commit('registerFailure');
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    loginSuccess(state: any, user: any) {
      state.status.loggedIn = true;
      state.user = user;
    },
    loginFailure(state: any) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state: any) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state: any) {
      state.status.loggedIn = false;
    },
    registerFailure(state: any) {
      state.status.loggedIn = false;
    }
  }
};
