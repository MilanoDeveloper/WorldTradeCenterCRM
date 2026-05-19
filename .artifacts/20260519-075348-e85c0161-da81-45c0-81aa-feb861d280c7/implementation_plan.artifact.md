# Plano de Implementação - Sincronização de Mensagens e Logs

Este plano visa resolver o problema onde Operadores e Clientes não conseguem visualizar as mensagens uns dos outros, além de adicionar logs detalhados para facilitar a depuração.

## Mudanças Propostas

### [Backend]

#### [MessageRepository.java](file:///C:/dev/repositorios/WTC/WTFBackEnd/work/work/src/main/java/br/com/fiap/wtc/work/repository/MessageRepository.java)
- Substituir o método de busca gerado por uma query MongoDB explícita utilizando `@Query`.
- Isso garante que a lógica de "OR" entre as combinações de (sender, receiver) e (receiver, sender) seja executada corretamente, independentemente da complexidade do nome do método.

```java
@Query("{ '$or': [ { 'senderId': ?0, 'receiverId': ?1 }, { 'senderId': ?1, 'receiverId': ?0 } ] }")
List<Message> findConversation(String user1, String user2, Sort sort);
```

#### [MessageService.java](file:///C:/dev/repositorios/WTC/WTFBackEnd/work/work/src/main/java/br/com/fiap/wtc/work/service/MessageService.java)
- Adicionar logs do SLF4J para registrar quando uma mensagem é enviada e quando uma conversa é solicitada.
- Atualizar a chamada para o novo método do repositório.

### [Android App]

#### [ChatViewModel.kt](file:///C:/dev/repositorios/WTC/WorldTradeCenterCRM/app/src/main/java/br/com/fiap/challengewtcc/viewmodel/ChatViewModel.kt)
- Adicionar logs (`println` ou `Log.d`) para monitorar o polling de mensagens.
- Registrar os IDs dos usuários sendo consultados para garantir que não haja inversão ou IDs nulos.

#### [ChatScreen.kt](file:///C:/dev/repositorios/WTC/WorldTradeCenterCRM/app/src/main/java/br/com/fiap/challengewtcc/ui/theme/screens/chat/ChatScreen.kt)
- Adicionar logs no `LaunchedEffect` para confirmar o início do polling e o ID do usuário de destino.

---

## Plano de Verificação

### Verificação Manual
1. **Reiniciar Backend**: Garantir que as novas classes e queries sejam aplicadas.
2. **Monitorar Logs**:
   - No Logcat do Android, verificar: `ChatViewModel: Carregando mensagens para USARIO_A e USUARIO_B`.
   - No Console do Spring Boot, verificar: `Buscando conversa entre ID1 e ID2`.
3. **Teste de Fluxo**:
   - Logar como **Operador**, enviar mensagem para o Novo Cliente.
   - Logar como **Novo Cliente**, verificar se a mensagem aparece e responder.
   - Voltar ao **Operador** e verificar se a resposta aparece via polling automático.
