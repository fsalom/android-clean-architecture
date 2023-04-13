package es.rudo.androidbaseproject.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.R
import es.rudo.androidbaseproject.databinding.ItemCharacterBinding
import es.rudo.androidbaseproject.helpers.extensions.overrideColor

class CharactersAdapter(
    private val onCharacterClick: (Character) -> Unit
) : ListAdapter<Character, CharactersAdapter.ViewHolder>(ListAdapterCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onCharacterClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id.toLong()
    }

    class ViewHolder private constructor(val context: Context, private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            character: Character,
            onCharacterClick: (Character) -> Unit
        ) {
            binding.cardView.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.recycler_animation)

            binding.character = character

            binding.circleColor.background.overrideColor(context.getColor(
                if (character.status == STATUS_ALIVE) {
                    R.color.light_green
                } else {
                    android.R.color.holo_red_light
                }
            ))

            Glide.with(context)
                .load(character.image)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.imageCharacter)

            binding.cardView.setOnClickListener {
                onCharacterClick(character)
            }

            binding.executePendingBindings()
        }

        companion object {

            private const val STATUS_ALIVE = "Alive"

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(parent.context, binding)
            }
        }
    }

    class ListAdapterCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem == newItem
        }
    }
}