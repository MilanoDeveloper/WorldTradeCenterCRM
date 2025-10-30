package br.com.fiap.challengewtcc.data

import br.com.fiap.challengewtcc.data.models.AppNotification
import java.util.UUID

data class ChatCommand(
    val key: String,
    val label: String,
    val template: String,
    val description: String,
    val action: (String) -> Unit
)

object ChatCommands {
    val all: List<ChatCommand> = listOf(
        ChatCommand(
            key = "/promo",
            label = "🎁 Promoção",
            template = "Olá! Temos uma promoção especial para você: ",
            description = "Enviar promoção"
        ) { _ ->
            MockRepository.pushNotification(
                AppNotification(
                    id = UUID.randomUUID().toString(),
                    title = "🎁 Promoção Enviada",
                    body = "Promoção enviada com sucesso para o cliente!",
                    read = false
                )
            )
        },
        ChatCommand(
            key = "/boleto",
            label = "💳 Boleto",
            template = "Segue o link para gerar seu boleto: https://wtc.com.br/boleto/123456\n\nVencimento: 3 dias\nValor: R$ 150,00",
            description = "Enviar boleto"
        ) { _ ->
            MockRepository.pushNotification(
                AppNotification(
                    id = UUID.randomUUID().toString(),
                    title = "💳 Boleto Enviado",
                    body = "Link do boleto enviado para o cliente!",
                    read = false
                )
            )
        },
        ChatCommand(
            key = "/agradecer",
            label = "🙏 Agradecer",
            template = "Muito obrigado pelo seu contato! Estamos sempre à disposição. 😊\n\nEquipe WTC Association",
            description = "Mensagem de agradecimento"
        ) { _ ->
            MockRepository.pushNotification(
                AppNotification(
                    id = UUID.randomUUID().toString(),
                    title = "🙏 Agradecimento Enviado",
                    body = "Mensagem de agradecimento enviada!",
                    read = false
                )
            )
        },
        ChatCommand(
            key = "/status",
            label = "📦 Status",
            template = "Seu pedido está em: TRANSPORTE\n\n📍 Localização: São Paulo - SP\n🚚 Previsão de entrega: 2 dias\n📋 Código de rastreio: BR123456789",
            description = "Informar status do pedido"
        ) { _ ->
            MockRepository.pushNotification(
                AppNotification(
                    id = UUID.randomUUID().toString(),
                    title = "📦 Status Enviado",
                    body = "Informações de rastreamento enviadas ao cliente!",
                    read = false
                )
            )
        },
        ChatCommand(
            key = "/suporte",
            label = "🆘 Suporte",
            template = "Vou transferir você para o suporte especializado. Aguarde um momento por favor.",
            description = "Transferir para suporte"
        ) { _ ->
            MockRepository.pushNotification(
                AppNotification(
                    id = UUID.randomUUID().toString(),
                    title = "🆘 Transferência Realizada",
                    body = "Cliente transferido para equipe de suporte técnico!",
                    read = false
                )
            )
        }
    )

    fun findByPrefix(text: String): ChatCommand? =
        all.firstOrNull { text.startsWith(it.key) }
}
