let idTarefaEditando = null;
document.getElementById("send-button").onclick = function() {

    const tasks = JSON.parse(localStorage.getItem("tasks")) || [];

    if (idTarefaEditando === null) {
        const task = {
            id: Date.now(),
            titulo: document.getElementById("task-title").value,
            descricao: document.getElementById("task-description").value,
            status: document.getElementById("task-status").value,
        };

    

        tasks.push(task);
    } else {
        const task = tasks.find(function(task) {
            return task.id === idTarefaEditando;
        });

        task.titulo = document.getElementById("task-title").value;
        task.descricao = document.getElementById("task-description").value;
        task.status = document.getElementById("task-status").value;

        idTarefaEditando = null;
    }
    

 

    localStorage.setItem("tasks", JSON.stringify(tasks));

    document.getElementById("task-title").value = "";
    document.getElementById("task-description").value = "";
    document.getElementById("task-status").value = "TODO";
    mostrarTarefas();
    document.getElementById("send-button").textContent = "Adicionar tarefa";
}

function mostrarTarefas() {

    const tasks = JSON.parse(localStorage.getItem("tasks")) || [];

    document.getElementById("task-list").innerHTML = "";

    tasks.forEach(function(task) {

        document.getElementById("task-list").innerHTML += `
            <li class="task">

                <h3>Nome: ${task.titulo}</h3>

                <p>Descrição: ${task.descricao}</p>

                <span class="task-status">
                    Status: ${task.status}
                </span>

                <button class="edit-button" onclick="editarTarefa(${task.id})">
                    Editar
                </button>

                <button class="remove-button" onclick="removerTarefa(${task.id})">
                    Remover
                </button>

            </li>
        `;
    });
}

function removerTarefa(id) {

    const tasks = JSON.parse(localStorage.getItem("tasks")) || [];
    
    const novasTasks = tasks.filter(function(task) {
        return task.id !== id;
    });

    localStorage.setItem("tasks", JSON.stringify(novasTasks));

    mostrarTarefas();
}

function editarTarefa(id) {

    const tasks = JSON.parse(localStorage.getItem("tasks")) || [];

    const task = tasks.find(function(task) {
        return task.id === id;
    });

    document.getElementById("task-title").value = task.titulo;
    document.getElementById("task-description").value = task.descricao;
    document.getElementById("task-status").value = task.status;

    idTarefaEditando = id;
    document.getElementById("send-button").textContent = "Atualizar tarefa";
}

mostrarTarefas();