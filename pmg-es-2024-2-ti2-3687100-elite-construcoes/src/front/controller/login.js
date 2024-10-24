// Variáveis do login
var formLogin = document.getElementById("forms-login");
var btnLogin = document.getElementById("btn-login");
var emailLogin = document.querySelector(".email");
var senhaLogin = document.querySelector(".password");

// Endpoint
var urlLogin = "http://localhost:8080/cliente/login";

btnLogin.addEventListener("click", function (e) {
  e.preventDefault();

  var data = {
      email: emailLogin.value,
      senha: senhaLogin.value
  };

  axios.post(urlLogin, data)
      .then(response => {
          console.log('Dados retornados do servidor:', response.data); // Verifique aqui
          localStorage.setItem("token", response.data.token);
          
          // Acessa o id corretamente
          if (response.data.id) {
              localStorage.setItem("userId", response.data.id);
          } else {
              console.warn('userId não encontrado na resposta do servidor.');
          }

          alert("Login realizado com sucesso");
          console.log('Token armazenado:', localStorage.getItem("token"));
          console.log('userId armazenado:', localStorage.getItem("userId"));
          window.location.href = "home.html"; // Redirecionar após confirmar armazenamento
      })
      .catch(error => {
          if (error.response) {
              console.log("Data:", error.response.data);
              console.log("Status:", error.response.status);
              console.log("Headers:", error.response.headers);
          } else if (error.request) {
              console.log("Request:", error.request);
          } else {
              console.log("Error:", error.message);
          }
          console.log("Config:", error.config);
      });
});
