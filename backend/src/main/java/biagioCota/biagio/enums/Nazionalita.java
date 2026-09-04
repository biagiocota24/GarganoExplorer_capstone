package biagioCota.biagio.enums;

public enum Nazionalita {
    IT("Italia", "🇮🇹"),
    FR("Francia", "🇫🇷"),
    DE("Germania", "🇩🇪"),
    ES("Spagna", "🇪🇸"),
    GB("Regno Unito", "🇬🇧"),
    US("Stati Uniti", "🇺🇸"),
    CH("Svizzera", "🇨🇭"),
    AT("Austria", "🇦🇹"),
    NL("Paesi Bassi", "🇳🇱"),
    BE("Belgio", "🇧🇪"),
    PL("Polonia", "🇵🇱"),
    CZ("Repubblica Ceca", "🇨🇿"),
    GR("Grecia", "🇬🇷"),
    PT("Portogallo", "🇵🇹"),
    SE("Svezia", "🇸🇪"),
    NO("Norvegia", "🇳🇴"),
    DK("Danimarca", "🇩🇰"),
    FI("Finlandia", "🇫🇮"),
    JP("Giappone", "🇯🇵"),
    CN("Cina", "🇨🇳"),
    IN("India", "🇮🇳"),
    BR("Brasile", "🇧🇷"),
    MX("Messico", "🇲🇽"),
    CA("Canada", "🇨🇦"),
    AU("Australia", "🇦🇺"),
    OTHER("Altro", "🌍");

    private final String nome;
    private final String bandiera;

    Nazionalita(String nome, String bandiera) {
        this.nome = nome;
        this.bandiera = bandiera;
    }

    public String getNome() {
        return nome;
    }

    public String getBandiera() {
        return bandiera;
    }
}
