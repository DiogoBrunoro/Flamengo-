const formulario = document.querySelector("form");
const InomeCompleto = document.querySelector(".nomeCompleto");
const InomeUsuario = document.querySelector(".nomeUsuario");
const IdataNascimento = document.querySelector(".dataNascimento");
const Iemail = document.querySelector(".email");
const Isenha = document.querySelector(".senhaPessoa");
const butaoCadastro = document.querySelector("button");

const InomeFornecedor = document.querySelector(".nomeFornecedor");
const Icnpj = document.querySelector(".cnpj");
const Iendereco = document.querySelector(".endereco");
const IemailCorporativo = document.querySelector(".emailCorporativo");
const IsenhaFornecedor = document.querySelector(".senhaFornecedor");


let isPessoa = true;

function toggleCadastro() {
    isPessoa = !isPessoa;

    const formTitle = document.getElementById('formTitle');
    const pessoaFields = document.getElementById('pessoaFields');
    const fornecedorFields = document.getElementById('fornecedorFields');
    const toggleButton = document.getElementById('toggleButton');

    if (isPessoa) {
        formTitle.innerText = 'CADASTRO DE PESSOA';
        pessoaFields.classList.remove('hidden');
        fornecedorFields.classList.add('hidden');

        document.getElementById('emailCorporativo').removeAttribute('required');
        document.getElementById('senhaFornecedor').removeAttribute('required');
        document.getElementById('confirmarSenhaFornecedor').removeAttribute('required');

        document.getElementById('email').setAttribute('required', true);
        document.getElementById('senhaPessoa').setAttribute('required', true);
        document.getElementById('confirmarSenhaPessoa').setAttribute('required', true);

        toggleButton.innerText = 'Alterar para Cadastro de Fornecedor';
    } else {
        formTitle.innerText = 'CADASTRO DE FORNECEDOR';
        pessoaFields.classList.add('hidden');
        fornecedorFields.classList.remove('hidden');

        document.getElementById('email').removeAttribute('required');
        document.getElementById('senhaPessoa').removeAttribute('required');
        document.getElementById('confirmarSenhaPessoa').removeAttribute('required');

        document.getElementById('emailCorporativo').setAttribute('required', true);
        document.getElementById('senhaFornecedor').setAttribute('required', true);
        document.getElementById('confirmarSenhaFornecedor').setAttribute('required', true);

        toggleButton.innerText = 'Alterar para Cadastro de Pessoa';
    }
}



function cadastrar() {
    if (isPessoa) {
        console.log(InomeCompleto.value);
        fetch("http://localhost:8080/cliente/register", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                nomeCompleto: InomeCompleto.value, 
                nomeUsuario: InomeUsuario.value,
                dataNascimento: IdataNascimento.value,
                email: Iemail.value,
                senha: Isenha.value
            })
        })
        .then(function (res) {
            if (res.ok) {
                console.log('Cadastro realizado com sucesso!');
                alert('Cadastro realizado com sucesso!');
                console.log('Redirecionando para a página de login...');
                window.location.href = 'login.html'; 
            } else if (res.status === 400) {
                console.log('Erro de validação.');
                alert('Por favor, verifique os campos e tente novamente.');
            } else {
                console.log('Erro no cadastro.');
                alert('Erro ao tentar realizar o cadastro. Tente novamente mais tarde.');
            }
        })
        .catch(function (error) {
            console.log('Erro na requisição:', error);
        });
    } else {
        console.log(IsenhaFornecedor.value);
        console.log(InomeFornecedor.value);
        console.log(Icnpj.value);
        fetch("http://localhost:8080/fornecedor/register", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                nomeFornecedor: InomeFornecedor.value,
                cnpj: Icnpj.value,
                endereco: Iendereco.value,
                emailCorporativo: IemailCorporativo.value,
                senhaFornecedor: IsenhaFornecedor.value
            })
        })
        .then(function (res) {
            if (res.ok) {
                console.log('Cadastro realizado com sucesso!');
                alert('Cadastro realizado com sucesso!');
                console.log('Redirecionando para a página de login...');
                window.location.href = 'login.html'; 
            } else if (res.status === 400) {
                console.log('Erro de validação.');
                alert('Por favor, verifique os campos e tente novamente.');
            } else {
                console.log('Erro no cadastro.');
                alert('Erro ao tentar realizar o cadastro. Tente novamente mais tarde.');
            }
        })
        .catch(function (error) {
            console.log('Erro na requisição:', error);
        });
    }
}


function limpar(){
    InomeCompleto.value = "",
    InomeUsuario.value = "",
    IdataNascimento.value = "",
    Iemail.value = "",
    Isenha.value = ""
}

formulario.addEventListener('submit', function (event){
    event.preventDefault();

    cadastrar();
    limpar();

})