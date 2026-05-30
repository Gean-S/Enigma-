package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle

data class AvatarData(
    val id: String,
    val name: String,
    val title: String,
    val resId: Int,
    val requiredScore: Int
)

data class RankEntry(
    val isUser: Boolean,
    val name: String,
    val score: Int,
    val title: String,
    val avatarId: String
)

@Composable
fun HomeScreen(
    viewModel: GameViewModel,
    onStartGame: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val progress = state.progress

    var activeTab by remember { mutableStateOf(0) } // 0 = Jornada, 1 = Ranking
    var showAvatarDialog by remember { mutableStateOf(false) }

    val avatarsList = listOf(
        AvatarData("novice", "Anão Explorador", "Iniciante", com.example.R.drawable.img_avatar_novice_1780097281782, 0),
        AvatarData("hermit", "Sábio Eremita", "Místico", com.example.R.drawable.img_avatar_hermit_1780097853889, 80),
        AvatarData("knight", "Cavaleiro Negro", "Guerreiro", com.example.R.drawable.img_avatar_knight_1780097297009, 170),
        AvatarData("paladin", "Paladino Dourado", "Celestial", com.example.R.drawable.img_avatar_paladin_1780097871776, 260),
        AvatarData("valkyrie", "Valkíria Rúnica", "Divina", com.example.R.drawable.img_avatar_valkyrie_1780097889514, 380),
        AvatarData("master", "Mestre Samurai", "Guardião", com.example.R.drawable.img_avatar_master_1780097311598, 500),
        AvatarData("dragon_lord", "Lorde Dragão", "Soberano", com.example.R.drawable.img_avatar_dragon_lord_1780097906114, 650)
    )

    fun getAvatarResId(avatarId: String): Int {
        return when (avatarId) {
            "hermit" -> com.example.R.drawable.img_avatar_hermit_1780097853889
            "knight" -> com.example.R.drawable.img_avatar_knight_1780097297009
            "paladin" -> com.example.R.drawable.img_avatar_paladin_1780097871776
            "valkyrie" -> com.example.R.drawable.img_avatar_valkyrie_1780097889514
            "master" -> com.example.R.drawable.img_avatar_master_1780097311598
            "dragon_lord" -> com.example.R.drawable.img_avatar_dragon_lord_1780097906114
            else -> com.example.R.drawable.img_avatar_novice_1780097281782
        }
    }

    fun getAvatarTitle(avatarId: String): String {
        return when (avatarId) {
            "hermit" -> "Sábio Eremita"
            "knight" -> "Cavaleiro Negro"
            "paladin" -> "Paladino Dourado"
            "valkyrie" -> "Valkíria Rúnica"
            "master" -> "Mestre Samurai"
            "dragon_lord" -> "Lorde Dragão"
            else -> "Anão Explorador"
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Jornada") },
                    label = { Text("Jornada", fontFamily = FontFamily.Serif) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = com.example.ui.theme.TextMuted,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = com.example.ui.theme.TextMuted
                    ),
                    modifier = Modifier.testTag("nav_jornada")
                )
                NavigationBarItem(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Ranking") },
                    label = { Text("Ranking Mundial", fontFamily = FontFamily.Serif) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = com.example.ui.theme.TextMuted,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = com.example.ui.theme.TextMuted
                    ),
                    modifier = Modifier.testTag("nav_ranking")
                )
            }
        }
    ) { paddingVals ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingVals)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp)
        ) {
            if (activeTab == 0) {
                // TAB 0: JORNADA SCREEN
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = "O CAMINHO DAS SOMBRAS",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 4.sp
                            ),
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )

                        Text(
                            text = "ENIGMA",
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                letterSpacing = 8.sp,
                                fontFamily = FontFamily.Serif
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text("──────── ⚔ ────────", color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 12.dp))

                        // Beautiful Medieval Profile grimoire card layout (Pure Display!)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .testTag("profile_card")
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                ) {
                                    // Profile cover/banner representing the creepy blood moon castle!
                                    coil.compose.AsyncImage(
                                        model = com.example.R.drawable.img_profile_cover_1780097347178,
                                        contentDescription = "Capa de Perfil",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(130.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    
                                    // Overlapping avatar based on selectedAvatar or falls back nicely
                                    coil.compose.AsyncImage(
                                        model = getAvatarResId(progress.selectedAvatar),
                                        contentDescription = "Avatar do Aventureiro",
                                        modifier = Modifier
                                            .size(96.dp)
                                            .align(Alignment.BottomCenter)
                                            .clip(CircleShape)
                                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = progress.name.uppercase(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    letterSpacing = 1.sp
                                )

                                Text(
                                    text = "CONQUISTA: ${getAvatarTitle(progress.selectedAvatar).uppercase()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    letterSpacing = 1.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Sabedoria: ${progress.score} Tomos",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily.Serif
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Section 1: Dedicated place to change avatar (Guarda-Roupa Rúnico!)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🎭", fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                                    Column {
                                        Text(
                                            text = "Guarda-Roupa Rúnico",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontFamily = FontFamily.Serif
                                        )
                                        Text(
                                            text = "Mude seu título e imagem de herói",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = com.example.ui.theme.TextMuted,
                                            fontFamily = FontFamily.Serif
                                        )
                                    }
                                }
                                Button(
                                    onClick = { showAvatarDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("change_avatar_button")
                                ) {
                                    Text("MUDAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = FontFamily.Serif)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Section 2: Dedicated portal to visit ranking (Portal dos Sábios / Hall of Fame)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { activeTab = 1 }
                                .testTag("visit_ranking_portal_card")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🏆", fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                                    Column {
                                        Text(
                                            text = "Salão dos Heróis",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontFamily = FontFamily.Serif
                                        )
                                        Text(
                                            text = "Visitar a classificação mundial",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = com.example.ui.theme.TextMuted,
                                            fontFamily = FontFamily.Serif
                                        )
                                    }
                                }
                                Text("➔", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }



                        Spacer(modifier = Modifier.height(16.dp))

                        if (progress.isFinished) {
                            Text(
                                text = "Você desvendou todos os segredos das sombras!",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 32.dp)
                            )
                        } else {
                            Text(
                                text = "Nível atual: ${progress.currentLevel} (Enigma #${progress.currentQuestionIndex + 1})",
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 32.dp)
                            )
                        }

                        Button(
                            onClick = onStartGame,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues(),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(56.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(com.example.ui.theme.BloodRed, com.example.ui.theme.DarkRedGradient)
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .testTag("play_button")
                        ) {
                            Text(
                                text = if (progress.currentLevel == "EASY" && progress.currentQuestionIndex == 0 && progress.score == 0) "INICIAR JORNADA" else "CONTINUAR",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (progress.score > 0) {
                            OutlinedButton(
                                onClick = { viewModel.resetGame() },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
                                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(56.dp)
                                    .testTag("reset_button")
                            ) {
                                Text("REINICIAR JORNADA", letterSpacing = 1.sp, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = "⚔️ Apenas os sábios sobrevivem às masmorras do esquecimento ⚔️",
                            color = com.example.ui.theme.TextMuted,
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(bottom = 24.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                // TAB 1: RANKING MUNDIAL (Leaderboards)
                val originalRankings = listOf(
                    RankEntry(false, "Lorde Valerius", 650, "Soberano Dragão", "dragon_lord"),
                    RankEntry(false, "Miyamoto Taro", 520, "Guardião Samurai", "master"),
                    RankEntry(false, "Valkíria Astrid", 395, "Divina Valkíria", "valkyrie"),
                    RankEntry(false, "Paladino Arthur", 270, "Celestial Templário", "paladin"),
                    RankEntry(false, "Sombra Roderick", 185, "Cavaleiro Negro", "knight"),
                    RankEntry(false, "Eremita Ignis", 90, "Místico Eremita", "hermit"),
                    RankEntry(false, "Dormin Barba-Longa", 45, "Anão Explorador", "novice"),
                    RankEntry(false, "Bardo Silas", 20, "Iniciante", "novice"),
                    RankEntry(false, "Servitor Pip", 5, "Recruta", "novice")
                )

                val userEntry = RankEntry(
                    isUser = true,
                    name = progress.name,
                    score = progress.score,
                    title = getAvatarTitle(progress.selectedAvatar),
                    avatarId = progress.selectedAvatar
                )

                // Dynamically sort everyone so user rises up in real-time
                val allRankings = (originalRankings + userEntry).sortedByDescending { it.score }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "LIVRO DOS SÁBIOS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 4.sp
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp).align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "RANKING MUNDIAL",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp).align(Alignment.CenterHorizontally)
                )

                Divider(color = com.example.ui.theme.BorderColor, thickness = 1.dp, modifier = Modifier.padding(bottom = 12.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                ) {
                    itemsIndexed(allRankings) { index, entry ->
                        val medal = when (index) {
                            0 -> "🥇"
                            1 -> "🥈"
                            2 -> "🥉"
                            else -> " ${index + 1} "
                        }

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (entry.isUser) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                width = if (entry.isUser) 2.dp else 1.dp,
                                color = if (entry.isUser) MaterialTheme.colorScheme.primary else com.example.ui.theme.BorderColor
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Position/Medal
                                Text(
                                    text = medal,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (entry.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.width(36.dp),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Avatar
                                coil.compose.AsyncImage(
                                    model = getAvatarResId(entry.avatarId),
                                    contentDescription = entry.name,
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .border(1.5.dp, if (entry.isUser) MaterialTheme.colorScheme.primary else Color.Transparent, CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                // Name & Title
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (entry.isUser) "${entry.name} (Você)" else entry.name,
                                        fontWeight = if (entry.isUser) FontWeight.ExtraBold else FontWeight.Bold,
                                        color = if (entry.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                        fontFamily = FontFamily.Serif,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = entry.title,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontSize = 11.sp
                                    )
                                }

                                // Score / Tomos
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = entry.score.toString(),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontFamily = FontFamily.Serif,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Tomos",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = com.example.ui.theme.TextMuted,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Interactive Dialog to view and equip unlocked avatars
    if (showAvatarDialog) {
        Dialog(onDismissRequest = { showAvatarDialog = false }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("avatar_dialog")
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GUARDA-ROUPA RÚNICO",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 2.sp
                        )
                    )

                    Text(
                        text = "Selecione seu Visual",
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Divider(color = com.example.ui.theme.BorderColor, modifier = Modifier.padding(bottom = 12.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 350.dp)
                    ) {
                        itemsIndexed(avatarsList) { idx, av ->
                            val isUnlocked = progress.score >= av.requiredScore
                            val isEquipped = progress.selectedAvatar == av.id

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isEquipped) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
                                ),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    width = 1.dp,
                                    color = if (isEquipped) MaterialTheme.colorScheme.primary else Color.Transparent
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable(enabled = isUnlocked) {
                                        viewModel.selectAvatar(av.id)
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box {
                                        coil.compose.AsyncImage(
                                            model = av.resId,
                                            contentDescription = av.name,
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(CircleShape)
                                                .border(2.dp, if (isEquipped) MaterialTheme.colorScheme.primary else Color.Transparent, CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                        if (!isUnlocked) {
                                            Box(
                                                modifier = Modifier
                                                    .matchParentSize()
                                                    .background(Color.Black.copy(alpha = 0.6f), CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    Icons.Default.Lock,
                                                    contentDescription = "Bloqueado",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = av.name,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif,
                                            color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else com.example.ui.theme.TextMuted,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = if (isUnlocked) "Conquistado!" else "Bloqueado: Requer ${av.requiredScore} Tomos",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (isUnlocked) MaterialTheme.colorScheme.primary else com.example.ui.theme.TextMuted,
                                            fontSize = 11.sp
                                        )
                                    }

                                    if (isUnlocked) {
                                        Text(
                                            text = if (isEquipped) "Equipado" else "Equipar",
                                            fontWeight = FontWeight.Bold,
                                            color = if (isEquipped) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                            fontSize = 13.sp,
                                            fontFamily = FontFamily.Serif
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showAvatarDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Fechar", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }


}
