package com.mrpowergamerbr.loritta.commands.vanilla.discord

import com.mrpowergamerbr.loritta.commands.AbstractCommand
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.Constants
import com.mrpowergamerbr.loritta.utils.LoriReply
import com.mrpowergamerbr.loritta.utils.isValidSnowflake
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.lorittaShards
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.Guild

class ServerIconCommand : AbstractCommand("servericon", listOf("guildicon", "iconeserver", "iconeguild", "iconedoserver", "iconedaguild", "íconedoserver", "iconedoservidor", "íconeguild", "íconedoserver", "íconedaguild", "íconedoservidor"), category = CommandCategory.DISCORD) {
	override fun getDescription(locale: BaseLocale): String {
		return locale.get("SERVERICON_DESCRIPTION")
	}

	override fun canUseInPrivateChannel(): Boolean {
		return false
	}

	override fun run(context: CommandContext, locale: BaseLocale) {
		var guild: Guild? = context.guild
		val id = if (context.args.isNotEmpty()) { context.args[0] } else { null }

		if (id != null && id.isValidSnowflake()) {
			guild = lorittaShards.getGuildById(context.args[0])
		}

		if (guild == null) {
			context.reply(
					LoriReply(
							message = context.locale["SERVERINFO_UnknownGuild", context.args[0]],
							prefix = Constants.ERROR
					)
			)
			return
		}

		if (guild.iconUrl == null) {
			context.reply(
					LoriReply(
							message = context.locale["SERVERICON_NoIcon", context.args[0]],
							prefix = Constants.ERROR
					)
			)
			return
		}

		val embed = EmbedBuilder()
		embed.setColor(Constants.DISCORD_BLURPLE) // Cor do embed (Cor padrão do Discord)
		val description = "**${context.locale["AVATAR_CLICKHERE", guild.iconUrl + "?size=2048"]}**"

		embed.setDescription(description)
		embed.setImage(guild.iconUrl) // Ícone da Guild
		embed.setColor(Constants.DISCORD_BLURPLE) // Cor do embed (Cor padrão do Discord)
		embed.setTitle("<:discord:314003252830011395> ${guild.name}", null) // Nome da Guild
		embed.setImage(guild.iconUrl + (if (!guild.iconUrl.endsWith(".gif")) "?size=2048" else ""))

		context.sendMessage(context.getAsMention(true), embed.build()) // phew, agora finalmente poderemos enviar o embed!
	}
}
