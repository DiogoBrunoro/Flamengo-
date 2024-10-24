// Função para carregar os dados do cliente
async function carregarDadosCliente() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        console.error("userId não encontrado no localStorage.");
        return; // Impede a execução da função se o userId não existir
    }

    const token = localStorage.getItem("token");
    const urlDadosCliente = `http://localhost:8080/cliente/${userId}`; // Corrigido para usar backticks

    const myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${token}`);

    const requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow"
    };

    try {
        const response = await fetch(urlDadosCliente, requestOptions);
        
        // Verificando se a resposta é válida
        if (!response.ok) {
            throw new Error('Erro ao buscar dados do cliente: ' + response.statusText);
        }

        const cliente = await response.json();
        console.log(cliente); // Verifique se os dados estão sendo retornados corretamente

        // Preenchendo os dados no HTML
        document.getElementById('nomeCompleto').textContent += cliente.nomeCompleto || '';
        document.getElementById('usuario').textContent += cliente.nomeUsuario || '';
        document.getElementById('dataNascimento').textContent += cliente.dataNascimento || '';
        document.getElementById('email').textContent += cliente.email || '';

        // Preenche os campos de edição
        document.getElementById('nomeCompletoInput').value = cliente.nomeCompleto || '';
        document.getElementById('usuarioInput').value = cliente.nomeUsuario || '';
        document.getElementById('dataNascimentoInput').value = cliente.dataNascimento || '';

    } catch (error) {
        console.error('Erro ao carregar os dados do cliente:', error);
    }
}

// Chama a função para carregar os dados ao carregar a página
document.addEventListener('DOMContentLoaded', function() {
    carregarDadosCliente();

    // Evento de clique no botão de salvar
    const btnSalvar = document.getElementById('editarPerfil');
    if (btnSalvar) {
        btnSalvar.addEventListener("click", async function (e) {
            e.preventDefault();
            await editarPerfil(); 
        });
    } else {
        console.error("Botão 'editarPerfil' não encontrado no DOM.");
    }
});

async function editarPerfil() {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem("token");
    
    // Verifica se o userId e o token estão disponíveis
    if (!userId || !token) {
        console.error("userId ou token não encontrados no localStorage.");
        return; // Impede a execução se não houver userId ou token
    }

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", `Bearer ${token}`);

    // Obtém os valores dos campos de entrada
    const nomeCompleto = document.getElementById('nomeCompletoInput').value;
    const nomeUsuario = document.getElementById('usuarioInput').value;
    const dataNascimento = document.getElementById('dataNascimentoInput').value;
    const senha = document.getElementById('senhaInput').value;
    const confirmarSenha = document.getElementById('senhaConfirmar').value;

    if (senhaFornecedor !== confirmarSenha) {
        alert("A senha e a confirmação de senha devem ser iguais.");
        return; // Impede a execução se as senhas não coincidirem
    }

    const raw = JSON.stringify({
        "nomeCompleto": nomeCompleto,
        "nomeUsuario": nomeUsuario,
        "dataNascimento": dataNascimento,
        ...(senha ? { "senha": senha } : {})
    });

    console.log(raw, "raw");

    const urledicaoPerfil = `http://localhost:8080/cliente/${userId}`;

    const requestOptions = {
        method: "PUT",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    try {
        const response = await fetch(urledicaoPerfil, requestOptions);
        
        
        if (!response.ok) {
            throw new Error('Erro ao atualizar perfil: ' + response.statusText);
        }
        
        const result = await response.text(); 
        console.log(response);
        console.log(result);
        alert("Perfil atualizado com sucesso");
        window.location.reload(); 
    } catch (error) {
        console.error('Erro ao editar perfil:', error);
    }
}

async function excluirPerfil() {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem("token");

    // Verifica se o userId e o token estão disponíveis
    if (!userId || !token) {
        console.error("userId ou token não encontrados no localStorage.");
        return; // Impede a execução se não houver userId ou token
    }

    const myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${token}`);

    const urlExcluirPerfil = `http://localhost:8080/cliente/delete/${userId}`;

    const requestOptions = {
        method: "DELETE",
        headers: myHeaders,
        redirect: "follow"
    };

    try {
        const response = await fetch(urlExcluirPerfil, requestOptions);
        
        if (!response.ok) {
            throw new Error('Erro ao excluir perfil: ' + response.statusText);
        }

        alert("Perfil excluído com sucesso");
        // Opcional: redirecionar para a página de login ou página inicial
        window.location.href = 'login.html'; // Altere para a página desejada após exclusão

    } catch (error) {
        console.error('Erro ao excluir perfil:', error);
    }
}

// Evento de clique no botão de excluir
const btnExcluir = document.getElementById('confirmarExclusao');
if (btnExcluir) {
    btnExcluir.addEventListener("click", async function (e) {
        e.preventDefault();
        await excluirPerfil(); 
    });
} else {
    console.error("Botão 'confirmarExclusao' não encontrado no DOM.");
}

function logout() {
    localStorage.removeItem('userId');
    localStorage.removeItem('token');
    window.location.href = 'login.html';
}

const btnLogout = document.getElementById('btnLogout');
if (btnLogout) {
    btnLogout.addEventListener("click", function (e) {
        e.preventDefault();
        logout();
    });
} else {
    console.error("Botão de logout não encontrado no DOM.");
}