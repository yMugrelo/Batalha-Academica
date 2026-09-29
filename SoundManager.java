import greenfoot.*;
import java.util.HashMap;

/**
 * SoundManager - o ÚNICO lugar do jogo que toca sons.
 *
 * Regras de segurança (para o jogo NUNCA depender de áudio):
 *   - só toca arquivos registrados em FILES (nome sem arquivo = silêncio)
 *   - se um som der erro ao carregar, o áudio é desligado e o jogo segue
 *   - a música só começa depois da primeira tecla ou clique do jogador:
 *     navegadores bloqueiam áudio antes de uma interação (HTML5)
 *   - tecla M liga/desliga o som a qualquer momento
 *
 * O jogo pede sons por um NOME LÓGICO ("battle", "attack"...) e a tabela
 * FILES diz qual arquivo de sounds/ toca para cada nome.
 *
 * QUANDO ADICIONAR UM SOM: coloque o arquivo (.wav ou .mp3) em sounds/ e
 * acrescente o par { "nome", "Arquivo.mp3" } em FILES.
 */
public class SoundManager
{
    /** Nome lógico -> arquivo que realmente existe dentro de sounds/. */
    private static final String[][] FILES = {
        { "battle",       "MusicaBatalha.mp3" },   // música em loop da batalha
        { "attack",       "Hit.mp3" },             // cada golpe
        { "enemyDeath",   "MorteInimigo.mp3" },    // golpe final na matéria
        { "studentDeath", "MorteAluno.mp3" },      // golpe final no aluno
        { "victory",      "Vitoria.mp3" },         // tela de vitória
        { "defeat",       "Derrota.mp3" },         // tela de derrota
    };

    /** Todos os nomes que o jogo sabe usar (para o relatório de ausentes). */
    private static final String[] EXPECTED = {
        "menu", "battle", "attack", "enemyDeath", "studentDeath", "victory", "defeat"
    };

    private static final int MUSIC_VOLUME = 55;
    private static final int EFFECT_VOLUME = 80;

    private static GreenfootSound music;         // música tocando agora
    private static String musicName;             // nome da música tocando agora
    private static String wantedMusic;           // música que a tela atual quer
    private static HashMap<String, GreenfootSound> effects = new HashMap<String, GreenfootSound>();

    private static boolean userInteracted = false;   // já houve tecla/clique?
    private static boolean muted = false;
    private static boolean disabled = false;         // um erro de áudio desliga tudo

    // ===================== Música =====================

    /**
     * Pede uma música em loop ("menu" ou "battle").
     * "menu" ainda não tem arquivo: os menus ficam em silêncio.
     * Se já estiver tocando, continua de onde está (não reinicia).
     */
    public static void playMusic(String name)
    {
        stopEffects();   // a vinheta da tela anterior não invade a próxima
        wantedMusic = name;
        startWantedMusic();
    }

    /** Para a música (ex.: fim da batalha). */
    public static void stopMusic()
    {
        wantedMusic = null;
        stopCurrentMusic();
    }

    // ===================== Efeitos =====================

    /**
     * Toca um efeito curto ("attack", "enemyDeath", "victory"...).
     */
    public static void playEffect(String name)
    {
        String file = fileFor(name);
        if (!canPlay() || file == null)
        {
            return;
        }
        try
        {
            GreenfootSound sound = effects.get(file);
            if (sound == null)
            {
                sound = new GreenfootSound(file);
                sound.setVolume(EFFECT_VOLUME);
                effects.put(file, sound);
            }
            sound.stop();   // recomeça do início se ainda estiver tocando
            sound.play();
        }
        catch (RuntimeException e)
        {
            disabled = true;   // áudio com problema: seguimos em silêncio
        }
    }

    /** Para todos os efeitos que ainda estão tocando. */
    public static void stopEffects()
    {
        for (GreenfootSound sound : effects.values())
        {
            sound.stop();
        }
    }

    // ===================== Controle =====================

    /**
     * Chamado pelo BaseWorld a cada tecla ou clique.
     * Na primeira vez, libera o áudio e começa a música pedida.
     */
    public static void onUserInput()
    {
        if (!userInteracted)
        {
            userInteracted = true;
            startWantedMusic();
        }
    }

    /** Liga/desliga o som (tecla M). */
    public static void toggleMute()
    {
        muted = !muted;
        if (muted)
        {
            stopCurrentMusic();
        }
        else
        {
            startWantedMusic();
        }
    }

    public static boolean isMuted()
    {
        return muted;
    }

    /** O Greenfoot foi pausado (botão Pause): pausa a música. */
    public static void pause()
    {
        if (music != null)
        {
            music.pause();
        }
    }

    /** O Greenfoot voltou a rodar: continua a música. */
    public static void resume()
    {
        if (music != null && canPlay())
        {
            music.playLoop();
        }
    }

    /**
     * Mostra no Terminal quais sons ainda faltam.
     * Botão direito na classe SoundManager > printMissingSounds().
     */
    public static void printMissingSounds()
    {
        for (String name : EXPECTED)
        {
            if (fileFor(name) == null)
            {
                System.out.println("Som ausente: \"" + name + "\"");
            }
        }
    }

    // ===================== Internos =====================

    private static boolean canPlay()
    {
        return userInteracted && !muted && !disabled;
    }

    /** Arquivo registrado para o nome lógico, ou null se não houver. */
    private static String fileFor(String name)
    {
        for (String[] entry : FILES)
        {
            if (entry[0].equals(name))
            {
                return entry[1];
            }
        }
        return null;
    }

    private static void startWantedMusic()
    {
        if (!canPlay() || wantedMusic == null)
        {
            return;
        }
        if (wantedMusic.equals(musicName) && music != null && music.isPlaying())
        {
            return;   // já está tocando a música certa
        }

        stopCurrentMusic();
        String file = fileFor(wantedMusic);
        if (file == null)
        {
            return;
        }
        try
        {
            music = new GreenfootSound(file);
            music.setVolume(MUSIC_VOLUME);
            music.playLoop();
            musicName = wantedMusic;
        }
        catch (RuntimeException e)
        {
            disabled = true;
            music = null;
        }
    }

    private static void stopCurrentMusic()
    {
        if (music != null)
        {
            music.stop();
        }
        music = null;
        musicName = null;
    }
}
