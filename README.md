# Batalha Acadêmica

> *Sobreviva ao semestre.*

RPG de turnos em **pixel art**, feito em **Java** com o **[Greenfoot](https://www.greenfoot.org/)** como projeto de faculdade.
Você controla um estudante universitário que precisa vencer quatro matérias — **Cálculo**, **Álgebra Linear**,
**Programação em Java** e **Estrutura de Dados** — cada uma com seus próprios ataques, e cada vez mais difícil.

O código foi escrito para ser **didático**: cada classe tem uma única responsabilidade, está comentada em
português e usa os conceitos de orientação a objetos de forma explícita (herança, polimorfismo,
encapsulamento, composição, enums e máquina de estados).

---

## Sumário

1. [Como executar](#como-executar)
2. [Como jogar](#como-jogar)
3. [Estrutura do repositório](#estrutura-do-repositório)
4. [Arquitetura](#arquitetura)
5. [Todas as classes](#todas-as-classes)
6. [Conceitos de orientação a objetos no projeto](#conceitos-de-orientação-a-objetos-no-projeto)
7. [Assets: imagens e sons](#assets-imagens-e-sons)
8. [Compatibilidade com o navegador (HTML5)](#compatibilidade-com-o-navegador-html5)
9. [Estado atual e próximos passos](#estado-atual-e-próximos-passos)

---

## Como executar

**Requisito:** [Greenfoot](https://www.greenfoot.org/download) 3.9 ou mais novo (o JDK já vem junto).

```bash
git clone https://github.com/yMugrelo/Batalha-Academica.git
```

1. Abra o Greenfoot e escolha **Scenario → Open**, selecionando a pasta clonada.
2. Clique em **Compile** (canto inferior direito).
3. Clique em **Run**.
4. Clique uma vez **dentro do jogo** para ele receber o teclado.

O jogo abre na tela inicial (`MyWorld`). Para testar uma batalha direto, clique com o botão direito na classe
`BattleWorld` e escolha `new BattleWorld()`.

> Os arquivos `.class` e `.ctxt` são gerados pelo Greenfoot ao compilar e ficam fora do repositório (`.gitignore`).

---

## Como jogar

Escolha uma matéria e reduza a vida dela a zero antes que ela faça o mesmo com você.
Cada turno você escolhe **uma ação**; depois a matéria responde com **um ataque**.

### Ações do estudante

| Tecla | Ação | Custo | Efeito |
|:-:|---|:-:|---|
| **1** | Resolver Questão | 5 de energia | Dano = conhecimento |
| **2** | Estudar | — | +10 energia, +3 conhecimento |
| **3** | Usar Conhecimento | 20 de energia | Dano = conhecimento × 2 |
| **4** | Descansar | — | +20 vida, +5 energia |

- **Acerto crítico:** ataques têm 15% de chance de causar +50% de força.
- **Dano recebido** por qualquer personagem = força do ataque − defesa de quem recebe (nunca menor que 0).
- Ação sem energia suficiente fica indisponível (aparece apagada com "SEM ENERGIA") e **não gasta o turno**.

**Atributos iniciais do estudante:** 100 de vida · 50 de energia · 10 de conhecimento · 5 de defesa.

### As matérias

Cada matéria começa com **0 de energia** e recupera **3 por turno** (máximo 10). A cada turno ela sorteia um
ataque **entre os que consegue pagar** — por isso a batalha começa com ataques fracos e vai esquentando.

| Matéria | Dificuldade | Vida | Força | Defesa | Ataques (dano extra / custo) |
|---|:-:|:-:|:-:|:-:|---|
| Cálculo | ■□□□ | 60 | 10 | 2 | Limite (+0/0) · Derivada (+4/3) · Integral (+8/6) |
| Álgebra Linear | ■■□□ | 70 | 11 | 3 | Matriz (+0/0) · Determinante (+4/3) · Autovalor (+9/7) |
| Programação em Java | ■■■□ | 85 | 12 | 4 | NullPointerException (+1/0) · **Infinite Loop** (+0/4, drena 10 de energia) · StackOverflowError (+10/8) |
| Estrutura de Dados | ■■■■ | 100 | 13 | 5 | Stack (+0/0) · Queue (+3/3) · Linked List (+6/5) · Binary Tree (+10/9) |

**Dica:** contra as matérias mais difíceis, só atacar não basta — estude primeiro e depois use o conhecimento.

### Controles

| Tecla | Função |
|---|---|
| `1` – `4` | Escolher ação na batalha |
| Setas (ou `W A S D`) | Navegar nos menus |
| `Enter` / `Espaço` | Confirmar |
| `Esc` | Voltar |
| `M` | Ligar/desligar o som |
| **Mouse** | Passar por cima seleciona, clicar confirma (em todos os menus) |

---

## Estrutura do repositório

Todas as classes ficam na raiz, porque o Greenfoot não usa pacotes. Elas se dividem em três grupos:

```
Batalha-Academica/
├── project.greenfoot      Configuração do cenário (mundo inicial, charset UTF-8)
├── README.md              Este arquivo (GitHub)
├── README.TXT             Resumo mostrado dentro do Greenfoot e no greenfoot.org
├── .gitignore
│
├── LÓGICA — regras do jogo, sem nada de desenho
│   ├── Student.java  Subject.java  Calculus.java  LinearAlgebra.java
│   ├── JavaProgramming.java  DataStructures.java  SubjectType.java
│   ├── Attack.java  EnergyDrainAttack.java  PlayerAction.java
│   ├── TurnManager.java  BattleState.java  BattleEvent.java  BattleSummary.java
│   └── GameManager.java
│
├── APRESENTAÇÃO — telas, interface, animações e efeitos
│   ├── Telas:       BaseWorld  MyWorld  SelectWorld  BattleWorld  ResultWorld
│   │                HowToPlayWorld  CreditsWorld
│   ├── Batalha:     BattleUI  HUD  StatBar  HealthBar  EnergyBar  DialogueBox  ActionMenu
│   ├── Menus:       MenuUI  SubjectCard  InfoPanel  Decoration  FloatingDecoration
│   ├── Personagens: CharacterSprite  PlayerSprite  BossSprite  AnimationState
│   └── Efeitos:     Effects  EffectSprite  ScreenOverlay  FadeTransition
│
├── RECURSOS — o único lugar que carrega arquivos ou define o visual
│   ├── ImageLibrary.java  PlaceholderArt.java  BackgroundManager.java
│   ├── SoundManager.java  Palette.java  UiArt.java  TextUtil.java  Keys.java
│
├── images/    PNGs do jogo (ainda vazia — ver "Assets")
└── sounds/    MP3s do jogo (música de batalha e efeitos — ver "Assets")
```

---

## Arquitetura

### 1. Fluxo de telas

Cada tela é um `World` do Greenfoot. Toda troca de tela passa pelo **`GameManager`**, e a transição
(escurecer → trocar → clarear) é feita pelo **`BaseWorld`**.

```
                ┌──────────────┐
                │  MyWorld     │  menu: JOGAR · COMO JOGAR · CRÉDITOS · SAIR
                └──────┬───────┘
          ┌────────────┼──────────────┬──────────────────┐
          ▼            ▼              ▼                  ▼
   ┌─────────────┐ ┌──────────────┐ ┌──────────────┐  "ATÉ LOGO!"
   │ SelectWorld │ │HowToPlayWorld│ │ CreditsWorld │  (Greenfoot.stop)
   └──────┬──────┘ └──────────────┘ └──────────────┘
          ▼  matéria escolhida (SubjectType)
   ┌─────────────┐
   │ BattleWorld │  cria Student + Subject + TurnManager + BattleUI
   └──────┬──────┘
          ▼  BattleSummary (resumo da batalha)
   ┌─────────────┐
   │ ResultWorld │  vitória: JOGAR NOVAMENTE → seleção
   └─────────────┘  derrota:  TENTAR NOVAMENTE → mesma batalha
                    MENU PRINCIPAL → menu
```

Os dados passam pelos **construtores** (a matéria escolhida, o resumo). Não existe estado global que possa
"sobrar" depois de um Reset.

### 2. O ciclo de um frame

O Greenfoot chama `act()` do mundo e depois de cada ator, várias vezes por segundo. Nada no jogo espera o
jogador em um laço: tudo é **contado em frames**.

```
BaseWorld.act()
 ├─ lê a tecla (Greenfoot.getKey) ──► onKey(tecla)      cada tela decide o que fazer
 ├─ verifica cliques ────────────────► handleMouse()
 └─ update()                                            contadores, lógica por frame
atores.act()                                            animações, barras, efeitos
```

### 3. A batalha: máquina de estados

O `TurnManager` guarda **um** estado (`BattleState`) e decide o próximo:

```
          jogador age e a matéria sobrevive
  PLAYER_TURN ─────────────────────────────► ENEMY_TURN
       ▲                                         │  (pausa de 40 frames)
       └─────────────────────────────────────────┘
          matéria ataca e o jogador sobrevive

  PLAYER_TURN ── matéria chega a 0 de vida ──► VICTORY  (fim)
  ENEMY_TURN  ── jogador chega a 0 de vida ──► DEFEAT   (fim)
```

Fora do `PLAYER_TURN`, as ações do jogador são ignoradas. Ação sem energia não muda o estado.

### 4. Separação lógica × apresentação

A lógica **nunca** desenha nada; a apresentação **nunca** altera a lógica.

```
   Teclado/Mouse
        │
        ▼
  BattleWorld ──► TurnManager.playerAction(ação)          LÓGICA
                     ├─ Student.performAction(...)          calcula dano, gasta energia
                     ├─ registra um BattleEvent             "o que aconteceu"
                     └─ muda o BattleState
        │
        ▼  a cada frame
  BattleUI.update()                                       APRESENTAÇÃO
     ├─ há BattleEvent novo? → animação de ataque
     │     └─ 10 frames depois: impacto (tremor, efeitos, número de dano)
     ├─ HUD: barras seguem os valores atuais
     ├─ DialogueBox: mensagem do TurnManager
     └─ ActionMenu: ativo só no turno do jogador e sem animação rodando
```

O `BattleEvent` é só um **registro de leitura** (quem agiu, dano, crítico, variações de vida/energia).
Assim a interface sabe qual animação tocar sem que a lógica precise conhecer a interface.

---

## Todas as classes

### Lógica do jogo

| Classe | Tipo | Responsabilidade |
|---|---|---|
| `Student` | classe | O estudante: vida, energia, conhecimento, defesa. Executa as 4 ações e sorteia o crítico. |
| `Subject` | classe **abstrata** | Base de todas as matérias: vida, força, defesa, energia e a **lista de ataques**. Escolhe e usa ataques. |
| `Calculus`, `LinearAlgebra`, `JavaProgramming`, `DataStructures` | `extends Subject` | Cada matéria só define seus números e cadastra seus ataques no construtor. |
| `SubjectType` | enum | Identifica as 4 matérias e cria o objeto certo (`create()`). |
| `Attack` | classe | Um ataque: nome, dano extra, custo de energia, descrição. `use()` aplica o dano. |
| `EnergyDrainAttack` | `extends Attack` | Ataque que também drena energia (Infinite Loop). Sobrescreve `use()`. |
| `PlayerAction` | enum com dados | As 4 ações: tecla, nome e custo. `fromKey("3")` converte tecla em ação. |
| `TurnManager` | classe | Máquina de estados da batalha, mensagens, estatísticas e registro de eventos. |
| `BattleState` | enum | `PLAYER_TURN`, `ENEMY_TURN`, `VICTORY`, `DEFEAT`. |
| `BattleEvent` | classe (imutável) | Registro da última ação, lido pela interface para animar. |
| `BattleSummary` | classe (imutável) | Resumo da batalha: turnos, dano causado/recebido, críticos, conhecimento obtido. |
| `GameManager` | classe (estática) | Única porta de troca de telas: menu, seleção, batalha, resultado, créditos. |

### Telas (`World`)

| Classe | Tela |
|---|---|
| `BaseWorld` | Base **abstrata** de todas as telas: tamanho 800×600, leitura de teclado/mouse, transições, ordem de desenho e pausa da música. |
| `MyWorld` | Menu principal (é o mundo que o Greenfoot abre primeiro). |
| `SelectWorld` | "Escolha sua matéria": 4 cards; o ícone da matéria selecionada flutua. |
| `BattleWorld` | Batalha: transforma teclas/cliques em ações e liga lógica e interface. |
| `ResultWorld` | Vitória (resumo + confete) ou derrota (estudante caído + matéria vencedora). |
| `HowToPlayWorld` | Regras, as 4 ações (mesmo visual da batalha) e controles. |
| `CreditsWorld` | Créditos com o estudante e as matérias desfilando. |

### Interface da batalha

| Classe | O que mostra |
|---|---|
| `BattleUI` | Monta a tela de batalha e transforma `BattleEvent`s em animações e efeitos. |
| `HUD` | Painéis do topo: nome, vida, energia e conhecimento do aluno; nome, vida e energia da matéria. |
| `StatBar` (abstrata) → `HealthBar`, `EnergyBar` | Barras animadas com "rastro" do dano. A de vida muda de cor (verde → amarela → vermelha). |
| `DialogueBox` | Caixa de mensagens semitransparente, com etiqueta do turno e texto aparecendo letra por letra. |
| `ActionMenu` | Grade 2×2 das ações: selecionada, disponível, indisponível, custo e efeito. Também tem um modo só de exibição. |

### Menus e elementos genéricos

| Classe | Uso |
|---|---|
| `MenuUI` | Lista de opções navegável por teclado e mouse (menu principal, resultado). |
| `SubjectCard` | Card da seleção: nome, dificuldade e descrição. |
| `InfoPanel` | Painel com título e linhas de texto. |
| `Decoration` / `FloatingDecoration` | Ator que só mostra uma imagem / a mesma coisa, flutuando. |

### Personagens e efeitos

| Classe | Uso |
|---|---|
| `CharacterSprite` (abstrata) | Máquina de estados de animação: parado, ataque (avança), dano (treme e pisca), derrota, comemoração. |
| `PlayerSprite` / `BossSprite` | O estudante (respira; na derrota tomba) / a matéria (flutua; na derrota afunda e some). |
| `AnimationState` | `IDLE`, `ATTACK`, `HURT`, `DEFEAT`, `CELEBRATE`. |
| `Effects` | Fábrica de efeitos: impacto, crítico, número flutuante, brilho, vitória, confete, derrota. |
| `EffectSprite` | Efeito temporário que se move, desbota e se remove sozinho. |
| `ScreenOverlay` | Clarão (crítico) e escurecimento (derrota). |
| `FadeTransition` | Camada preta das transições entre telas. |

### Recursos (visual, arquivos, utilidades)

| Classe | Uso |
|---|---|
| `ImageLibrary` | **Único** lugar que carrega imagens. Usa o PNG se ele estiver registrado; senão, a arte provisória. |
| `PlaceholderArt` | Arte provisória em **pixel art desenhada em código** (sprites definidos como grades de caracteres). |
| `BackgroundManager` | Fundo de cada tela, sempre ajustado a 800×600 **sem deformar** (amplia e recorta o centro). |
| `SoundManager` | **Único** lugar que toca sons. Música só depois da 1ª interação; tecla M; nunca quebra o jogo. |
| `Palette` | Todas as cores do jogo (consistência visual). |
| `UiArt` | Painéis com borda pixelada, sombras, escurecimento. |
| `TextUtil` | Texto em imagem, texto com sombra, quebra de linha. |
| `Keys` | Nomes das teclas (confirmar, voltar, setas, som). |

---

## Conceitos de orientação a objetos no projeto

| Conceito | Onde aparece |
|---|---|
| **Encapsulamento** | Atributos sempre `private`. A vida só muda por `takeDamage`/`heal`, que garantem `0 ≤ vida ≤ máximo`. Existem *getters*, mas nenhum *setter* público. |
| **Herança** | `Calculus extends Subject`, `EnergyDrainAttack extends Attack`, `HealthBar extends StatBar`, `PlayerSprite extends CharacterSprite`, todas as telas `extends BaseWorld`. |
| **Classes abstratas** | `Subject`, `StatBar`, `CharacterSprite` e `BaseWorld` não podem ser instanciadas: são "moldes" com partes que as filhas completam. |
| **Polimorfismo** | `Subject inimigo = new DataStructures();` — o `TurnManager` funciona com qualquer matéria. `attack.use(...)` roda a versão de `EnergyDrainAttack` quando é o Infinite Loop. Cada barra escolhe sua cor em `fillColor()`. |
| **Composição** | Uma `Subject` **tem** uma `ArrayList<Attack>`; a `BattleUI` **tem** `HUD`, `DialogueBox` e `ActionMenu`. |
| **Enums com dados** | `PlayerAction` guarda tecla, nome e custo; o menu, a tela "Como jogar" e a lógica leem do mesmo lugar. |
| **Máquina de estados** | `TurnManager` (`BattleState`) e `CharacterSprite` (`AnimationState`). |
| **Responsabilidade única** | Lógica não desenha; interface não muda regras; só `ImageLibrary` carrega imagens; só `SoundManager` toca sons; só `GameManager` troca telas. |
| **Um único ponto de verdade** | Custos e efeitos das ações estão escritos uma vez só; menu, tela de regras e lógica usam as mesmas constantes. |

---

## Assets: imagens e sons

**Situação atual:** a pasta `images/` está vazia — todo o visual é a **arte provisória** do `PlaceholderArt`.
A pasta `sounds/` já tem a música de batalha e os efeitos (os menus ainda ficam em silêncio).
Nada quebra por falta de arquivos.

### Como adicionar um PNG

1. Coloque o arquivo no caminho esperado (abaixo).
2. Registre o caminho na lista `AVAILABLE` de `ImageLibrary.java`, por exemplo `"player/player_idle.png",`.
3. Compile. O jogo passa a usar o PNG no lugar da arte provisória.

| Caminho | Uso | Tamanho sugerido |
|---|---|---|
| `images/player/player_idle.png` | Estudante parado | ~96×144 |
| `images/player/player_attack.png` | Estudante atacando | mesma altura do idle |
| `images/player/player_hurt.png` | Estudante levando dano | mesma altura do idle |
| `images/player/player_defeat.png` | Estudante derrotado (já deitado) | livre |
| `images/subjects/calculus.png`, `linear_algebra.png`, `java_programming.png`, `data_structures.png` | As 4 matérias | ~140×140 |
| `images/backgrounds/classroom.png` | Menu e telas de informação | 800×600 |
| `images/backgrounds/calculus_room.png`, `algebra_room.png`, `java_lab.png`, `data_structures_room.png` | Arenas | 800×600 |
| `images/backgrounds/final_exam_room.png` | Reservado (boss final, ainda não existe) | 800×600 |
| `images/effects/hit.png`, `critical.png`, `victory.png` | Efeitos | livre |

Para pixel art, exporte os PNGs **já no tamanho final** (ampliação "nearest neighbor"). Redimensionar no
código pode borrar os pixels. Fundos com outro tamanho são ajustados automaticamente, sem deformar.

### Sons

O jogo pede cada som por um **nome lógico**; a tabela `FILES` de `SoundManager.java` diz qual arquivo toca.

| Nome | Arquivo | Quando toca |
|---|---|---|
| `menu` | *(nenhum ainda)* | Música em loop no menu, seleção, regras e créditos |
| `battle` | `MusicaBatalha.mp3` | Música em loop na batalha |
| `attack` | `Hit.mp3` | A cada golpe (do aluno e da matéria) |
| `enemyDeath` | `MorteInimigo.mp3` | Golpe final na matéria |
| `studentDeath` | `MorteAluno.mp3` | Golpe final no aluno |
| `victory` / `defeat` | `Vitoria.mp3` / `Derrota.mp3` | Ao abrir a tela de resultado |

Para adicionar um som, coloque o `.wav` ou `.mp3` em `sounds/` e registre o par `{ "nome", "Arquivo.mp3" }` em `FILES`.

Para listar o que falta, clique com o botão direito na classe `ImageLibrary` → `printMissingAssets()`,
ou em `SoundManager` → `printMissingSounds()`.

---

## Compatibilidade com o navegador (HTML5)

O objetivo é publicar no [greenfoot.org](https://www.greenfoot.org/) para jogar direto no navegador. Por isso o
código segue estas regras:

- **Nada bloqueia o jogo:** sem `Thread.sleep`, sem threads, sem `while` esperando tecla. Pausas, animações
  e a vez da matéria são contadas em frames.
- **Só a API do Greenfoot** (`GreenfootImage`, `GreenfootSound`, `Greenfoot.getKey`, `Greenfoot.mouseClicked`…),
  sem bibliotecas externas, sem leitura de arquivos e sem reflexão.
- **Caminhos relativos** e recursos dentro do próprio cenário.
- **Arquivos só são carregados se estiverem registrados**, porque o comportamento de um arquivo ausente
  pode ser diferente no navegador.
- **Texto** criado com `new GreenfootImage(texto, tamanho, cor, fundo)`, sem depender de fontes instaladas.
- **Som** só depois da primeira tecla ou clique (regra dos navegadores). Se o áudio falhar, o jogo segue em silêncio.

**Ainda não foi testado no navegador:** os símbolos ∫ e λ desenhados nos quadros, a largura exata dos textos
com a fonte do navegador e o desempenho em máquinas lentas. No navegador é preciso **clicar no jogo**
antes de usar o teclado.

---

## Estado atual e próximos passos

**Pronto:**

- [x] Sistema de batalha por turnos completo, com crítico e 13 ataques diferentes
- [x] Menu, seleção, batalha, resultado, como jogar e créditos
- [x] HUD com barras animadas, caixa de mensagens, menu de ações
- [x] Animações de ataque, dano, derrota e comemoração; efeitos de impacto, crítico, vitória e derrota
- [x] Transições entre telas, teclado e mouse
- [x] Sistema de som com música de batalha e efeitos (falta a música do menu)

**Próximos passos:**

- [ ] Arte definitiva (PNGs) e música do menu
- [ ] Publicar no greenfoot.org e testar no navegador
- [ ] Revisar o equilíbrio (hoje o jogo tende a ser fácil; descansar sempre empata com Estrutura de Dados)
- [ ] Ideias: modo "semestre" (as 4 matérias em sequência), boss final (Prova Final), XP

---

## Autor

**(Murilo Rosa de Paula e Felipe Henrique Santos Berberth)** — projeto da disciplina de *(Prog II)*, *(UENP)*.

Feito com [Greenfoot](https://www.greenfoot.org/).
