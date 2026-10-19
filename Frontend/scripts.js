let idTarefaEditando = null;

function obterTarefas() {
    return JSON.parse(localStorage.getItem("tasks")) || [];
}

function salvarTarefas(tasks) {
    localStorage.setItem("tasks", JSON.stringify(tasks));
}

function obterDadosFormulario() {
    return {
        titulo: document.getElementById("task-title").value,
        descricao: document.getElementById("task-description").value,
        dataTermino: document.getElementById("task-end-date").value,
        prioridade: document.getElementById("task-priority").value,
        categoria: document.getElementById("task-category").value,
        status: document.getElementById("task-status").value
    };
}

function limparFormulario() {
    document.getElementById("task-title").value = "";
    document.getElementById("task-description").value = "";
    document.getElementById("task-end-date").value = "";
    document.getElementById("task-priority").value = "1";
    document.getElementById("task-category").value = "";
    document.getElementById("task-status").value = "TODO";
}

document.getElementById("send-button").onclick = function() {
    const tasks = obterTarefas();
    const dadosTarefa = obterDadosFormulario();

    if (idTarefaEditando === null) {
        const task = {
            id: Date.now(),
            ...dadosTarefa
        };

        tasks.push(task);
    } else {
        const task = tasks.find(function(task) {
            return task.id === idTarefaEditando;
        });

        if (task) {
            Object.assign(task, dadosTarefa);
        }

        idTarefaEditando = null;
    }

    salvarTarefas(tasks);
    limparFormulario();

    mostrarTarefas();

    document.getElementById("send-button").textContent = "Adicionar tarefa";
};

function criarElementoTarefa(task) {
    let botaoRemover = "";

    if (task.id !== idTarefaEditando) {
        botaoRemover = `
            <button class="remove-button" onclick="removerTarefa(${task.id})">
                Remover
            </button>
        `;
    }

    const itemTarefa = document.createElement("li");
    itemTarefa.className = "task";

    itemTarefa.innerHTML = `
        <input type="checkbox" value="${task.id}">

        <h3></h3>

        <p class="descricao"></p>

        <p class="data-termino"></p>

        <p class="prioridade"></p>

        <p class="categoria"></p>

        <span class="task-status"></span>

        <button class="edit-button" onclick="editarTarefa(${task.id})">
            Editar
        </button>

        ${botaoRemover}
    `;

    itemTarefa.querySelector("h3").textContent =
        `Nome: ${task.titulo}`;

    itemTarefa.querySelector(".descricao").textContent =
        `Descrição: ${task.descricao}`;

    itemTarefa.querySelector(".data-termino").textContent =
        `Data de término: ${task.dataTermino}`;

    itemTarefa.querySelector(".prioridade").textContent =
        `Prioridade: ${task.prioridade}`;

    itemTarefa.querySelector(".categoria").textContent =
        `Categoria: ${task.categoria}`;

    itemTarefa.querySelector(".task-status").textContent =
        `Status: ${task.status}`;

    return itemTarefa;
}

function mostrarTarefas() {
    const tasks = obterTarefas();
    const listaTarefas = document.getElementById("task-list");

    listaTarefas.innerHTML = "";

    tasks.forEach(function(task) {
        const itemTarefa = criarElementoTarefa(task);
        listaTarefas.append(itemTarefa);
    });
}

function removerTarefa(id) {
    if (id === idTarefaEditando) return;

    const tasks = obterTarefas();

    const novasTasks = tasks.filter(function(task) {
        return task.id !== id;
    });

    salvarTarefas(novasTasks);
    mostrarTarefas();
}

function preencherFormulario(task) {
    document.getElementById("task-title").value = task.titulo;
    document.getElementById("task-description").value = task.descricao;
    document.getElementById("task-end-date").value = task.dataTermino;
    document.getElementById("task-priority").value = task.prioridade;
    document.getElementById("task-category").value = task.categoria;
    document.getElementById("task-status").value = task.status;
}

function editarTarefa(id) {
    const tasks = obterTarefas();

    const task = tasks.find(function(task) {
        return task.id === id;
    });

    if (!task) return;

    preencherFormulario(task);

    idTarefaEditando = id;
    document.getElementById("send-button").textContent = "Atualizar tarefa";

    mostrarTarefas();
}

document.getElementById("change-status-button").onclick = function() {
    const tasks = obterTarefas();
    const checkboxes = document.querySelectorAll("#task-list input[type='checkbox']");
    const novoStatus = document.getElementById("status-select").value;

    checkboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
            const task = tasks.find(function(task) {
                return task.id == checkbox.value;
            });

            if (task) {
                task.status = novoStatus;
            }
        }
    });

    salvarTarefas(tasks);
    mostrarTarefas();
};

mostrarTarefas();