Usage guide and a java docs:

REQUIRE:
 - Spigot 1.8
 
 
 Java Docs:
 
 Kit - Para criar um novo kit deverá se fazer um extends da class Kit e implementar os methods em falta.
 Cooldown - A criação de um cooldown é bem mais básica do que criar um hashmap e fazer uma data de runnables, simplesmente criar um object da class Ability e definir os parametros
 Weapon Class - Esta class server para uma criação mais rápida dos icons usados para os kits, é simplesmente um enum com vários itemstacks
 Icon Class - Apenas um itemstack builder
 
Examples:
  Kit:
    public class nomeKit extends Kit{}
  Cooldown:
    public Ability cooldown = new Ability(charges,time,TimeUnit.something);
    Ver se o jogador está em cooldown ou é preciso aplicar a mensagem de cooldown:
      if(cooldown.tryUse(player)){
        //Faz alguma coisa
      }else{
       Util.getInstance().sendCooldownMessage(p, cooldown, TimeUnit.something, true(use action bar));
      }