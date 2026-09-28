import greenfoot.*;

/**
 * HUD - os painéis de status no topo da batalha.
 *
 *   ┌ ALUNO        CON 10 ┐            ┌ CÁLCULO             ┐
 *   │ HP [########] 100/100│            │ HP [########]  60/60 │
 *   └ EN [######--]  50/50 ┘            └ EN [--------]   0/10 ┘
 *
 * Não é um Actor: ele cria os atores (painéis e barras), coloca no
 * mundo e, a cada frame, repassa os valores atuais para as barras.
 */
public class HUD
{
    private static final int PANEL_WIDTH = 330;
    private static final int PANEL_HEIGHT = 98;
    private static final int MARGIN = 10;
    private static final int BAR_WIDTH = 196;

    private Student student;
    private Subject subject;

    private HealthBar playerHealth;
    private EnergyBar playerEnergy;
    private HealthBar subjectHealth;
    private EnergyBar subjectEnergy;
    private Decoration knowledgeLabel;
    private int shownKnowledge;

    public HUD(World world, Student student, Subject subject)
    {
        this.student = student;
        this.subject = subject;

        int leftX = MARGIN + PANEL_WIDTH / 2;
        int rightX = world.getWidth() - MARGIN - PANEL_WIDTH / 2;
        int panelY = 8 + PANEL_HEIGHT / 2;

        // Painéis de fundo com o nome
        world.addObject(new Decoration(panelImage("ALUNO")), leftX, panelY);
        world.addObject(new Decoration(panelImage(subject.getName().toUpperCase())), rightX, panelY);

        // Barras
        playerHealth = new HealthBar(BAR_WIDTH);
        playerEnergy = new EnergyBar(BAR_WIDTH);
        subjectHealth = new HealthBar(BAR_WIDTH);
        subjectEnergy = new EnergyBar(BAR_WIDTH);

        // As barras ficam centralizadas no painel, abaixo do nome
        world.addObject(playerHealth, leftX, panelY + 6);
        world.addObject(playerEnergy, leftX, panelY + 30);
        world.addObject(subjectHealth, rightX, panelY + 6);
        world.addObject(subjectEnergy, rightX, panelY + 30);

        // Conhecimento: não tem máximo, então aparece como número (roxo) ao lado do nome
        knowledgeLabel = new Decoration(knowledgeImage(student.getKnowledge()));
        world.addObject(knowledgeLabel, leftX + PANEL_WIDTH / 2 - 60, panelY - 28);
        shownKnowledge = student.getKnowledge();

        // Começa com os valores certos, sem animação
        playerHealth.snapTo(student.getHealth(), student.getMaxHealth());
        playerEnergy.snapTo(student.getEnergy(), student.getMaxEnergy());
        subjectHealth.snapTo(subject.getHealth(), subject.getMaxHealth());
        subjectEnergy.snapTo(subject.getEnergy(), subject.getMaxEnergy());
    }

    /**
     * Chamado a cada frame pelo BattleUI. As barras animam sozinhas.
     */
    public void update()
    {
        playerHealth.setValue(student.getHealth(), student.getMaxHealth());
        playerEnergy.setValue(student.getEnergy(), student.getMaxEnergy());
        subjectHealth.setValue(subject.getHealth(), subject.getMaxHealth());
        subjectEnergy.setValue(subject.getEnergy(), subject.getMaxEnergy());

        if (student.getKnowledge() != shownKnowledge)
        {
            shownKnowledge = student.getKnowledge();
            knowledgeLabel.setImage(knowledgeImage(shownKnowledge));
        }
    }

    private GreenfootImage panelImage(String name)
    {
        GreenfootImage img = UiArt.panel(PANEL_WIDTH, PANEL_HEIGHT);
        img.drawImage(TextUtil.text(name, 20, Palette.HIGHLIGHT), 14, 10);
        return img;
    }

    private GreenfootImage knowledgeImage(int knowledge)
    {
        return TextUtil.text("CON " + knowledge, 18, Palette.KNOWLEDGE);
    }
}
