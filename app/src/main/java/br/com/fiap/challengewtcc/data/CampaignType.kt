package br.com.fiap.challengewtcc.data

enum class CampaignType { PROMOCAO, COMUNICADO, ALERTA, BOLETO, AGRADECIMENTO }

fun CampaignType.emoji(): String = when (this) {
    CampaignType.PROMOCAO -> "🎁"
    CampaignType.COMUNICADO -> "📣"
    CampaignType.ALERTA -> "⚠️"
    CampaignType.BOLETO -> "💳"
    CampaignType.AGRADECIMENTO -> "🙏"
}

fun CampaignType.template(): String = when (this) {
    CampaignType.PROMOCAO -> "Olá! Temos uma promoção especial esperando por você."
    CampaignType.COMUNICADO -> "Comunicado importante sobre sua conta."
    CampaignType.ALERTA -> "Alerta: verifique suas informações recentes."
    CampaignType.BOLETO -> "Segue o link para gerar seu boleto: https://exemplo.com/boleto/123456"
    CampaignType.AGRADECIMENTO -> "Obrigado pelo contato! Estamos à disposição. 😊"
}
