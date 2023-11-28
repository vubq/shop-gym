import axios from 'axios';

const API_URL = 'http://localhost:7070/webapi/auth/';

class AuthService {
  login(user: any): any {
    return axios
      .post(API_URL + 'signin', {
        userName: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user: any) {
    return axios.post(API_URL + 'signup', {
      username: user.username,
      email: user.email,
      password: user.password
    });
  }
}

export default new AuthService();
