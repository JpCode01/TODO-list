const CONFIG_EMAIL = {
    serviceId: "service_dhkkyv6",
    templateId: "template_ufqje5m",
    publicKey: "GlnLENrtUrdV5pZaX"
};

emailjs.init({
    publicKey: CONFIG_EMAIL.publicKey
});

async function enviarEmailCriacaoTarefa(task, emailDestino) {
    const parametrosEmail = {
        name: "TODO List",
        time: new Date().toLocaleString("pt-BR"),
        message: `
        Uma nova tarefa foi criada na sua TODO List!

        Título: ${task.titulo}
        Descrição: ${task.descricao}
        Data de término: ${task.dataTermino}
        Prioridade: ${task.prioridade}
        Categoria: ${task.categoria}
        Status: ${task.status}
        `.trim(),
        email_destino: emailDestino
    };

    return emailjs.send(
        CONFIG_EMAIL.serviceId,
        CONFIG_EMAIL.templateId,
        parametrosEmail
    );
}
