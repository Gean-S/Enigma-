package com.example.model

data class Riddle(
    val id: Int,
    val question: String,
    val answer: String,
    val hint: String
)

val easyRiddles = listOf(
    Riddle(1, "O que é, o que é: quanto mais seca, mais molhada fica?", "A toalha", "Usada após o banho."),
    Riddle(2, "O que é, o que é: cai em pé e corre deitado?", "A chuva", "Vem das nuvens."),
    Riddle(3, "O que é, o que é: tem dentes mas não morde?", "O pente", "Usado no cabelo."),
    Riddle(4, "O que é, o que é: sobe quando a chuva desce?", "O guarda-chuva", "Protege da água."),
    Riddle(5, "O que é, o que é: anda sem pernas e fala sem boca?", "O telefone", "Usado para comunicação."),
    Riddle(6, "O que é, o que é: quanto mais você tira, maior fica?", "O buraco", "Pode estar no chão."),
    Riddle(7, "O que é, o que é: tem braço mas não abraça?", "A cadeira", "Onde você se senta."),
    Riddle(8, "O que é, o que é: tem cabeça e tem dente, não é bicho nem gente?", "O alho", "Espanta vampiros."),
    Riddle(9, "O que é, o que é: vive cheio de buracos, mas ainda consegue segurar água?", "A esponja", "Usada para lavar a louça."),
    Riddle(10, "O que é, o que é: entra na água e não se molha?", "A sombra", "Segue os seus passos."),
    Riddle(11, "O que é, o que é: tem cidades, rios e florestas, mas não tem pessoas?", "O mapa", "Guia o caminho."),
    Riddle(12, "O que é, o que é: sempre aumenta mas nunca diminui?", "A idade", "Comemore a cada ano."),
    Riddle(13, "O que é, o que é: pode passar diante do sol sem fazer sombra?", "O vento", "Você sente mas não vê."),
    Riddle(14, "O que é, o que é: é feito para andar e não anda?", "A rua", "Os carros passam por ela."),
    Riddle(15, "O que é, o que é: fala todas as línguas?", "O eco", "Repete o que você diz."),
    Riddle(16, "O que é, o que é: nasce grande e morre pequeno?", "O lápis", "Usado para escrever."),
    Riddle(17, "O que é, o que é: quanto mais cresce, menos se vê?", "A escuridão", "A falta de luz."),
    Riddle(18, "O que é, o que é: tem folhas mas não é árvore?", "O livro", "Está cheio de histórias."),
    Riddle(19, "O que é, o que é: tem olho mas não vê?", "A agulha", "Usada para costurar."),
    Riddle(20, "O que é, o que é: tem asas mas não voa?", "O ventilador", "Gira para fazer vento."),
    Riddle(21, "O que é, o que é: fica no meio do ovo?", "A letra v", "Não é a gema..."),
    Riddle(22, "O que é, o que é: quanto mais você usa, mais fino fica?", "O sabonete", "Usado para lavar o corpo."),
    Riddle(23, "O que é, o que é: é seu, mas os outros usam mais que você?", "Seu nome", "Como te chamam."),
    Riddle(24, "O que é, o que é: tem pernas mas não anda?", "A mesa", "Onde você faz as refeições."),
    Riddle(25, "O que é, o que é: corre o tempo todo mas nunca sai do lugar?", "O relógio", "Marca as horas."),
    Riddle(26, "O que é, o que é: tem coroa mas não é rei?", "O abacaxi", "É uma fruta cítrica."),
    Riddle(27, "O que é, o que é: pode encher uma sala mas não ocupa espaço?", "A luz", "Derrota as trevas."),
    Riddle(28, "O que é, o que é: perde a cabeça pela manhã e a ganha à noite?", "O travesseiro", "Você dorme sobre ele."),
    Riddle(29, "O que é, o que é: tem boca mas não fala?", "O rio", "Tem leito mas não dorme."),
    Riddle(30, "O que é, o que é: pula de galho em galho e não é macaco?", "O fogo", "Destrói florestas."),
    Riddle(51, "O que é, o que é: dão-nos uma vez e temos para sempre, mas se tentamos vender ninguém compra?", "Conselho", "Palavras de amigos."),
    Riddle(52, "O que é, o que é: de dia tem quatro pés, de noite tem seis, e às vezes mais?", "A cama", "Onde você descansa."),
    Riddle(53, "O que é, o que é: um prato de vidro que cai no chão e não quebra?", "O gelo", "Água congelada."),
    Riddle(54, "O que é, o que é: passa a vida inteira no topo, mas nunca sobe degraus?", "O chapéu", "Fica na cabeça."),
    Riddle(55, "O que é, o que é: entra na boca de todo mundo, mas ninguém consegue comer?", "A colher", "Utensílio de mesa.")
)

val hardRiddles = listOf(
    Riddle(31, "O que é, o que é: quanto mais perto estamos dela, menos conseguimos vê-la?", "O futuro", "Ainda não chegou."),
    Riddle(32, "O que é, o que é: existe uma vez em um minuto, duas vezes em um momento e nenhuma vez em cem anos?", "A letra m", "É uma consoante."),
    Riddle(33, "O que é, o que é: pode quebrar sem nunca ter sido tocado?", "A promessa", "Feita com palavras."),
    Riddle(34, "O que é, o que é: quanto mais você compartilha, menos você tem?", "O segredo", "Guarde para si."),
    Riddle(35, "O que é, o que é: viaja o mundo inteiro ficando sempre no mesmo canto?", "O selo", "Fica no envelope."),
    Riddle(36, "O que é, o que é: tem chave mas não abre porta?", "O teclado", "Usado para digitar ou tocar."),
    Riddle(37, "O que é, o que é: se alimenta de letras?", "O correio", "Entrega correspondências."),
    Riddle(38, "O que é, o que é: quando precisamos jogar fora, compramos; quando precisamos usar, jogamos fora?", "A âncora", "Prende o navio."),
    Riddle(39, "O que é, o que é: quanto mais rápido corre, mais para trás fica?", "O rastro", "Marcas deixadas pelo caminho."),
    Riddle(40, "O que é, o que é: é invisível, mas pode encher um lugar?", "O silêncio", "A ausência de som."),
    Riddle(41, "O que é, o que é: tem quatro dedos e um polegar, mas não é vivo?", "A luva", "Protege as mãos."),
    Riddle(42, "O que é, o que é: entra duro e sai mole, pingando?", "O macarrão", "Comida italiana."),
    Riddle(43, "O que é, o que é: pode ser contado, mas não pode ser visto?", "O tempo", "Passa depressa."),
    Riddle(44, "O que é, o que é: se você disser meu nome eu desapareço?", "O silêncio", "Oposto do ruído."),
    Riddle(45, "O que é, o que é: nunca volta, embora nunca vá embora?", "O passado", "Fica na memória."),
    Riddle(46, "O que é, o que é: tem início, meio e fim, mas não tem começo nem término?", "O círculo", "Forma geométrica."),
    Riddle(47, "O que é, o que é: nasce da água e morre na água?", "O sal", "Usado para temperar."),
    Riddle(48, "O que é, o que é: quanto mais vazio está, mais pesado fica?", "O caminhão de lixo", "Inversamente proporcional... a carga é lixo pesado? (Ou a consciência)."),
    Riddle(49, "O que é, o que é: todos podem abrir, mas ninguém consegue fechar depois de aberta?", "A conversa", "Troca de palavras."),
    Riddle(56, "O que é, o que é: quanto mais longo se deita, mais curto fica em pé?", "O sapato", "Você usa nos pés."),
    Riddle(57, "O que é, o que é: tem dentes mas não come, tem barba mas não se barbeia?", "O milho", "Delícia na espiga."),
    Riddle(58, "O que é, o que é: tem cauda mas não é cão, voa sem asas e viaja no escuro?", "O cometa", "Corpo celeste no espaço."),
    Riddle(59, "O que é, o que é: sobe o morro, desce o morro, e não sai do lugar?", "A estrada", "Caminho pavimentado.")
)

val impossibleRiddle = Riddle(
    id = 50,
    question = "O que é a vida pra você?",
    answer = "ANY",
    hint = "Não existe resposta errada. Siga a sua intuição."
)
